package com.architecture.feature_users.list

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.architecture.core.model.User
import com.architecture.core.state.UiState
import org.junit.Rule
import org.junit.Test

class UserListScreenKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun displaysUserListItemInformation() {
        with(composeTestRule) {
            setContent { UserListItem(user = testUserItem) {} }
            onNodeWithText("Laura").assertIsDisplayed()
            onNodeWithText("laura@gmail.com").assertIsDisplayed()
        }
    }

    @Test
    fun displaysUserMainViewUserList() {
        with(composeTestRule) {
            setContent { UserMainView(state = uiStateSuccess, {}, {}) }
            onNodeWithText("Laura").assertIsDisplayed()
            onNodeWithText("laura@gmail.com").assertIsDisplayed()
        }
    }

    @Test
    fun displaysUserMainViewLoading() {
        with(composeTestRule) {
            setContent { UserMainView(state = uiStateLoading, {}, {}) }
            onNodeWithTag("CircularProgressIndicator").assertIsDisplayed()
        }
    }

    @Test
    fun displaysUserMainViewError() {
        with(composeTestRule) {
            setContent { UserMainView(state = uiStateError, {}, {}) }
            onNodeWithText("No internet connection").assertIsDisplayed()
        }
    }

}

private val testUserItem = User(
    name = "Laura",
    email = "laura@gmail.com",
    picture = ""
)

private val uiStateSuccess = UiState.Success(listOf(testUserItem))
private val uiStateLoading = UiState.Loading
private val uiStateError = UiState.Error.NoConnection("No internet connection")
