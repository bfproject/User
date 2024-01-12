package com.architecture.feature_users.single

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.architecture.core.model.User
import com.architecture.core.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class UserSingleViewModel @Inject constructor() :  ViewModel() {

    private val _uiStateFlow: MutableStateFlow<UiState<User>> = MutableStateFlow(UiState.Loading)
    val uiStateFlow: StateFlow<UiState<User>> = _uiStateFlow
    private val actionFlow: MutableSharedFlow<UserSingleUiAction> = MutableSharedFlow()

    init {
        viewModelScope.launch {
            actionFlow.collect {
                handleAction(it)
            }
        }
    }

    fun submitAction(action: UserSingleUiAction) {
        viewModelScope.launch {
            actionFlow.emit(action)
        }
    }

    private fun handleAction(action: UserSingleUiAction) {
        when (action) {
            is UserSingleUiAction.Load -> {
                loadUser(action.userName, action.email, action.picture)
            }
        }
    }

    private fun submitState(state: UiState<User>) {
        viewModelScope.launch {
            _uiStateFlow.value = state
        }
    }

    private fun loadUser(userName: String, email: String, picture: String) {
        submitState(UiState.Success(User(email, userName, picture)))
    }

}
