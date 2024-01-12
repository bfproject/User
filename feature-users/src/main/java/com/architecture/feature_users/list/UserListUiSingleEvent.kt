package com.architecture.feature_users.list

import com.architecture.core.state.UiSingleEvent

sealed class UserListUiSingleEvent : UiSingleEvent {

    data class OpenUserScreen(val navRoute: String) : UserListUiSingleEvent()

}
