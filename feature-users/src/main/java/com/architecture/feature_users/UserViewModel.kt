package com.architecture.feature_users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.architecture.core.model.User
import com.architecture.core.repository.UserRepository
import com.architecture.core.state.DataState
import com.architecture.core.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository) :  ViewModel() {

    private val _uiStateFlow: MutableStateFlow<UiState<List<User>>> = MutableStateFlow(UiState.Loading)
    val uiStateFlow: StateFlow<UiState<List<User>>> = _uiStateFlow
    private val actionFlow: MutableSharedFlow<UserUiAction> = MutableSharedFlow()

    init {
        viewModelScope.launch {
            actionFlow.collect {
                handleAction(it)
            }
        }
    }

    fun submitAction(action: UserUiAction) {
        viewModelScope.launch {
            actionFlow.emit(action)
        }
    }

    private fun handleAction(action: UserUiAction) {
        when (action) {
            is UserUiAction.Load -> {
                loadUserList()
            }
        }
    }

    private fun submitState(state: UiState<List<User>>) {
        viewModelScope.launch {
            _uiStateFlow.value = state
        }
    }

    private fun loadUserList() {
        viewModelScope.launch {
            userRepository.getUserList().collect {
                when (it) {
                    is DataState.Success -> submitState(UiState.Success(it.data))
                }
            }
        }
    }

}
