package com.architecture.feature_users.single

import com.architecture.core.state.UiAction

sealed class UserSingleUiAction : UiAction {

    data class Load(
        val userName: String,
        val email: String,
        val picture: String
    ) : UserSingleUiAction()

}
