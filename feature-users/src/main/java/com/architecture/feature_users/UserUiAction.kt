package com.architecture.feature_users

import com.architecture.core.state.UiAction

sealed class UserUiAction: UiAction {

    object Load : UserUiAction()

}
