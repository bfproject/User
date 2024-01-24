package com.architecture.feature_users.list

import app.cash.turbine.test
import com.architecture.core.model.User
import com.architecture.core.navigation.NavRoutes
import com.architecture.core.navigation.UserInput
import com.architecture.core.repository.UserRepository
import com.architecture.core.state.DataState
import com.architecture.core.state.UiState
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
class UserViewModelTest {

    private lateinit var userRepository: UserRepository
    private lateinit var viewModel: UserViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        userRepository = mock(UserRepository::class.java)
        viewModel = UserViewModel(userRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `WHEN action Load is received THEN state contains list of users`() = runTest {
        // Given
        val userList = listOf(User("test@email.com", "Test User", "test_picture"))
        `when`(userRepository.getUserList()).thenReturn(flowOf(DataState.Success(userList)))

        viewModel.uiStateFlow.test {
            // When
            // The UserViewModel submit Load action in init block when it is instantiated

            // Then
            // Assert Loading state
            assertEquals(UiState.Loading, awaitItem())
            // Assert Success state
            assertEquals(UiState.Success(userList), awaitItem())

            // No more items should be emitted
            expectNoEvents()
        }
    }

    @Test
    fun `WHEN action Search is received THEN state contains list of match users`() = runTest {
        // Given
        val userList = listOf(
            User("test1@email.com", "Test User 1", "test1_picture"),
            User("test2@email.com", "Test User 2", "test2_picture")
        )
        `when`(userRepository.getUserList()).thenReturn(flowOf(DataState.Success(userList)))

        viewModel.uiStateFlow.test {
            // When
            // The UserViewModel submit Load action in init block when it is instantiated
            viewModel.submitAction(UserUiAction.Search("Test User 1"))

            // Then
            assertEquals(UiState.Loading, awaitItem())
            assertEquals(UiState.Success(userList), awaitItem())
            assertEquals(UiState.Success(listOf(userList[0])), awaitItem())
            expectNoEvents()
        }
    }

    @Test
    fun `WHEN action Search is received THEN state contains empty list of users`() = runTest {
        // Given
        val userList = listOf(
            User("test1@email.com", "Test User 1", "test1_picture"),
            User("test2@email.com", "Test User 2", "test2_picture")
        )
        `when`(userRepository.getUserList()).thenReturn(flowOf(DataState.Success(userList)))

        viewModel.uiStateFlow.test {
            // When
            // The UserViewModel submit Load action in init block when it is instantiated
            viewModel.submitAction(UserUiAction.Search("Nonexistent User"))

            // Then
            // skip loading and user list item events
            skipItems(2)
            assertEquals(UiState.Success(emptyList<User>()), awaitItem())
            expectNoEvents()
        }
    }


    @Test
    fun `WHEN action UserClick is received THEN state contains OpenUserScreen single event`() = runTest {
        // Given
        val userList = listOf(
            User("test1@email.com", "Test User 1", "test1_picture"),
            User("test2@email.com", "Test User 2", "test2_picture")
        )
        val user = userList[0]
        `when`(userRepository.getUserList()).thenReturn(flowOf(DataState.Success(userList)))

        viewModel.singleEventFlow.test {
            // When
            viewModel.submitAction(UserUiAction.UserClick(user))

            // Then
            assertEquals(
                awaitItem(), UserListUiSingleEvent.OpenUserScreen(
                    NavRoutes.User.routeForUser(
                        UserInput(
                            user.name,
                            user.email,
                            URLEncoder.encode(user.picture, StandardCharsets.UTF_8.toString())
                        )
                    )
                )
            )

            expectNoEvents()
        }
    }

}
