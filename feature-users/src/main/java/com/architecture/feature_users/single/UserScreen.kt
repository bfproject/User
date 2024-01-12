package com.architecture.feature_users.single

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.architecture.core.model.User
import com.architecture.core.navigation.UserInput
import com.architecture.core.state.UiState

@Composable
fun UserScreen(
    viewModel: UserSingleViewModel,
    userInput: UserInput,
) {

    viewModel.uiStateFlow.collectAsStateWithLifecycle().value.let {
        when (it) {
            is UiState.Success -> {
                UserDetails(it.data)
            }

            else -> {}
        }
    }

    LaunchedEffect(Unit) {
        viewModel.submitAction(UserSingleUiAction.Load(userInput.userName, userInput.email, userInput.picture))
    }

}

@Composable
fun UserDetails(user: User) {
    // TODO: Implement UI to match the design
    ElevatedCard(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp, horizontal = 16.dp),
        ) {
            Text(modifier = Modifier.fillMaxWidth(), text = user.name)
            Text(user.email)
        }
    }
}

@Preview(showBackground = false)
@Composable
fun UserDetailsPreview() {
    UserDetails(user = User("email", "name", "url"))
}
