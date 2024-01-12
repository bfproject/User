package com.architecture.feature_users.single

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.architecture.core.model.User
import com.architecture.core.state.UiState

@Composable
fun UserScreen(viewModel: UserSingleViewModel) {

    viewModel.uiStateFlow.collectAsStateWithLifecycle().value.let {
        when (it) {
            is UiState.Success -> {
                UserDetails(it.data)
            }

            else -> {}
        }
    }

    LaunchedEffect(Unit) {
        viewModel.submitAction(UserSingleUiAction.Load)
    }

}

@Composable
fun UserDetails(user: User) {
    // TODO: Implement UI to match the design
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = user.name)
        Text(text = user.email)
    }
}
