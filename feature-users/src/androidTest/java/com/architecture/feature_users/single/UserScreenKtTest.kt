package com.architecture.feature_users.single

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.architecture.core.model.User
import org.junit.Rule
import org.junit.Test

class UserScreenKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun displaysUserInformation() {
        with(composeTestRule) {
            setContent { UserDetails(user = testUserItem) }
            onNodeWithText("Laura").assertIsDisplayed()
            onNodeWithText("laura@gmail.com").assertIsDisplayed()
        }
    }

}

private val testUserItem = User(
    name = "Laura",
    email = "laura@gmail.com",
    picture = ""
)
