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
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
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
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        userRepository = mock(UserRepository::class.java)
        viewModel = UserViewModel(userRepository)
    }

    @After
    fun tearDown() {
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `WHEN action Load is received THEN state contains list of users`() =
        testDispatcher.runBlockingTest {
            // Given
            val userList = listOf(User("test@email.com", "Test User", "test_picture"))
            `when`(userRepository.getUserList()).thenReturn(flowOf(DataState.Success(userList)))

            // When
            viewModel.submitAction(UserUiAction.Load)

            // Then
            viewModel.uiStateFlow.test {
                // Assert Success state
                assertEquals(UiState.Success(userList), expectMostRecentItem())

                // No more items should be emitted
                expectNoEvents()
            }
        }

    @Test
    fun `WHEN action Search is received THEN state contains list of match users`() = testDispatcher.runBlockingTest {
        // Given
        val userList = listOf(
            User("test1@email.com", "Test User 1", "test1_picture"),
            User("test2@email.com", "Test User 2", "test2_picture")
        )
        `when`(userRepository.getUserList()).thenReturn(flowOf(DataState.Success(userList)))

        // When
        viewModel.submitAction(UserUiAction.Load)
        viewModel.submitAction(UserUiAction.Search("Test User 1"))

        // Then
        viewModel.uiStateFlow.test {
            assertEquals(UiState.Success(listOf(userList[0])), expectMostRecentItem())
            expectNoEvents()
        }
    }

    @Test
    fun `WHEN action Search is received THEN state contains empty list of users`() =
        testDispatcher.runBlockingTest {
            // Given
            val userList = listOf(
                User("test1@email.com", "Test User 1", "test1_picture"),
                User("test2@email.com", "Test User 2", "test2_picture")
            )
            `when`(userRepository.getUserList()).thenReturn(flowOf(DataState.Success(userList)))

            // When
            viewModel.submitAction(UserUiAction.Load)
            viewModel.submitAction(UserUiAction.Search("Nonexistent User"))

            // Then
            viewModel.uiStateFlow.test {
                assertEquals(UiState.Success(emptyList<User>()), expectMostRecentItem())
                expectNoEvents()
            }
        }


    @Test
    fun `WHEN action UserClick is received THEN state contains OpenUserScreen single event`() =
        testDispatcher.runBlockingTest {
            // Given
            val user = User("test@email.com", "Test User", "test_picture")

            // When
            viewModel.submitAction(UserUiAction.UserClick(user))

            // Then
            viewModel.singleEventFlow.test {
                assertEquals(
                    expectMostRecentItem(), UserListUiSingleEvent.OpenUserScreen(
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
