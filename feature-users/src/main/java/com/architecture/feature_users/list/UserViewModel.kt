package com.architecture.feature_users.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.architecture.core.model.User
import com.architecture.core.navigation.NavRoutes
import com.architecture.core.navigation.UserInput
import com.architecture.core.repository.UserRepository
import com.architecture.core.state.DataState
import com.architecture.core.state.UiState
import com.architecture.core.state.asUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private val _uiStateFlow: MutableStateFlow<UiState<List<User>>> = MutableStateFlow(UiState.Loading)
    val uiStateFlow: StateFlow<UiState<List<User>>> = _uiStateFlow
    private val actionFlow: MutableSharedFlow<UserUiAction> = MutableSharedFlow()
    private val _singleEventFlow = Channel<UserListUiSingleEvent>()
    val singleEventFlow = _singleEventFlow.receiveAsFlow()

    private var userListToSearch = listOf<User>()

    init {
        viewModelScope.launch {
            actionFlow.collect {
                handleAction(it)
            }
        }
        submitAction(UserUiAction.Load)
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

            is UserUiAction.Search -> {
                searchUser(action.query)
            }

            is UserUiAction.UserClick -> {
                submitSingleEvent(
                    UserListUiSingleEvent.OpenUserScreen(
                        NavRoutes.User.routeForUser(
                            UserInput(
                                action.user.name,
                                action.user.email,
                                URLEncoder.encode(action.user.picture, StandardCharsets.UTF_8.toString())
                            )
                        )
                    )
                )
            }
        }
    }

    private fun searchUser(query: String) {
        var result = listOf<User>()
        if (userListToSearch.isNotEmpty()) {
            result = userListToSearch
                .filter { it.name.contains(query, ignoreCase = true)  || it.email.contains(query, ignoreCase = true) }
        }
        submitState(UiState.Success(result))
    }

    private fun submitState(state: UiState<List<User>>) {
        viewModelScope.launch {
            _uiStateFlow.value = state
        }
    }

    private fun loadUserList() {
        viewModelScope.launch {
            submitState(UiState.Loading)
            userRepository.getUserList().collect {
                when (it) {
                    is DataState.Success -> {
                        userListToSearch = it.data
                        submitState(UiState.Success(it.data))
                    }
                    is DataState.Error -> submitState(it.asUiState())
                }
            }
        }
    }

    private fun submitSingleEvent(event: UserListUiSingleEvent) {
        viewModelScope.launch {
            _singleEventFlow.send(event)
        }
    }

}
