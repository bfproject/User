package com.architecture.feature_users.single

import app.cash.turbine.test
import com.architecture.core.model.User
import com.architecture.core.state.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
class UserSingleViewModelTest {

    private lateinit var viewModel: UserSingleViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel = UserSingleViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `WHEN action Load is received THEN state contains user details`() = runTest {
        // Given
        val userName = "Test User 1"
        val email = "test1@email.com"
        val picture = "test1_picture"

        viewModel.uiStateFlow.test {
            // When
            viewModel.submitAction(UserSingleUiAction.Load(userName, email, picture))

            // Then
            assertEquals(UiState.Loading, awaitItem())
            assertEquals(UiState.Success(User(email, userName, picture)), awaitItem())

            // No more items should be emitted
            expectNoEvents()
        }

    }

}
