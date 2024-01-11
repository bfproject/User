package com.architecture.feature_users

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.architecture.core.model.User
import com.architecture.core.state.UiState

@Composable
fun UserListScreen(viewModel: UserViewModel) {

    viewModel.uiStateFlow.collectAsStateWithLifecycle().value.let {
        when (it) {
            is UiState.Success -> {
                LazyColumn {
                    items(it.data) { item ->
                        UserListItem(item)
                    }
                }
            }

            else -> {}
        }
    }

    LaunchedEffect(Unit) {
        viewModel.submitAction(UserUiAction.Load)
    }

}

@Composable
fun UserListItem(user: User) {
    ElevatedCard(modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp, horizontal = 16.dp)
        ) {
            Text(user.name)
            Text(user.email)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserListItemPreview() {
    UserListItem(user = User("email", "name", "url"))
}
