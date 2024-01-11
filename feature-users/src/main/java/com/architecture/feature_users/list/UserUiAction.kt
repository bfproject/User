package com.architecture.feature_users.list

import com.architecture.core.state.UiAction

sealed class UserUiAction: UiAction {

    object Load : UserUiAction()

}
