package com.architecture.feature_users.list

import androidx.lifecycle.viewModelScope
import com.architecture.core.model.User
import com.architecture.core.navigation.NavRoutes
import com.architecture.core.navigation.UserInput
import com.architecture.core.repository.UserRepository
import com.architecture.core.state.BaseViewModel
import com.architecture.core.state.DataState
import com.architecture.core.state.UiState
import com.architecture.core.state.asUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel<List<User>, UiState<List<User>>, UserUiAction, UserListUiSingleEvent>() {

    private var userListToSearch = listOf<User>()

    override fun initState(): UiState<List<User>> = UiState.Loading

    init {
        submitAction(UserUiAction.Load)
    }

    override fun handleAction(action: UserUiAction) {
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
                .filter { it.name.contains(query, ignoreCase = true) || it.email.contains(query, ignoreCase = true) }
        }
        submitState(UiState.Success(result))
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

}
