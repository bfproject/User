package com.architecture.feature_users.list

import com.architecture.core.model.User
import com.architecture.core.state.UiAction

sealed class UserUiAction: UiAction {

    object Load : UserUiAction()

    data class UserClick(val user: User) : UserUiAction()

}
