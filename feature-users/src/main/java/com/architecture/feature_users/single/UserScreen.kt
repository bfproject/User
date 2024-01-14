package com.architecture.feature_users.single

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.architecture.core.model.User
import com.architecture.core.navigation.UserInput
import com.architecture.core.state.UiState
import com.architecture.feature_users.R
import com.architecture.feature_users.common.CustomImage

@Composable
fun UserScreen(
    viewModel: UserSingleViewModel,
    userInput: UserInput,
) {

    viewModel.uiStateFlow.collectAsStateWithLifecycle().value.let {
        when (it) {
            is UiState.Success -> {
                UserDetailMainView(it, it.data.name)
            }

            else -> {}
        }
    }

    LaunchedEffect(Unit) {
        viewModel.submitAction(UserSingleUiAction.Load(userInput.userName, userInput.email, userInput.picture))
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailMainView(
    state: UiState<User>,
    appBarTitle: String = "",
) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = appBarTitle,
                    fontWeight = FontWeight.Bold
                )
            })
        },
        content = {
            Box(
                modifier = Modifier.padding(it),
            ) {
                UserContent(state)
            }
        }
    )
}

@Composable
fun UserContent(state: UiState<User>) {
    // TODO: Implement UI to match the design
    state.let {
        when (it) {
            is UiState.Success -> {
                UserDetails(it.data)
            }

            else -> {}
        }
    }
}

@Composable
fun UserDetails(user: User) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            CustomImage(
                url = user.picture,
                modifier = Modifier
                    .size(84.dp)
                    .clip(CircleShape)
            )
        }

        Spacer(modifier = Modifier.size(16.dp))

        ColumnItem(
            title = stringResource(R.string.user_name_title),
            data =  user.name,
            imageVector = Icons.Outlined.AccountCircle,
        )

        Spacer(modifier = Modifier.size(16.dp))

        ColumnItem(
            title = stringResource(R.string.user_email_title),
            data =  user.email,
            imageVector = Icons.Outlined.Email,
        )
    }
}

@Composable
fun ColumnItem(
    title: String,
    data:String,
    imageVector: ImageVector,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(56.dp)
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.size(16.dp))

        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.size(6.dp))

            Text(
                text = data,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
    Divider(
        modifier = Modifier.padding(start = 88.dp, end = 16.dp)
    )
}

@Preview(showBackground = false)
@Composable
fun UserDetailsPreview() {
    UserDetails(user = User("email", "name", "url"))
}
