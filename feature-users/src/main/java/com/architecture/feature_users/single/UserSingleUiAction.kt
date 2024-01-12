package com.architecture.feature_users.single

import com.architecture.core.state.UiAction

sealed class UserSingleUiAction : UiAction {

    object Load: UserSingleUiAction()

}
