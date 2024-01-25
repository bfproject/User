package com.architecture.feature_users.single

import com.architecture.core.model.User
import com.architecture.core.state.BaseViewModel
import com.architecture.core.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserSingleViewModel @Inject constructor() :
    BaseViewModel<User, UiState<User>, UserSingleUiAction, Nothing>() {

    override fun initState(): UiState<User> = UiState.Loading

    override fun handleAction(action: UserSingleUiAction) {
        when (action) {
            is UserSingleUiAction.Load -> {
                loadUser(action.userName, action.email, action.picture)
            }
        }
    }

    private fun loadUser(userName: String, email: String, picture: String) {
        submitState(UiState.Success(User(email, userName, picture)))
    }

}
