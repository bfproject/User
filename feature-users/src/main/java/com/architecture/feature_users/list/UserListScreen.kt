package com.architecture.feature_users.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.architecture.core.model.User
import com.architecture.core.state.UiState
import com.architecture.feature_users.R
import com.architecture.feature_users.common.CustomCircularProgressIndicator
import com.architecture.feature_users.common.CustomImage
import kotlinx.coroutines.flow.collectLatest

@Composable
fun UserListScreen(viewModel: UserViewModel, navController: NavController) {

    val state = viewModel.uiStateFlow.collectAsStateWithLifecycle().value
    val lazyPagingUserItems = viewModel.pagingDataFlow.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        viewModel.singleEventFlow.collectLatest {
            when (it) {
                is UserListUiSingleEvent.OpenUserScreen -> {
                    navController.navigate(it.navRoute)
                }
            }
        }
    }

    UserMainView(
        state = state,
        lazyPagingUserItems = lazyPagingUserItems,
        onUserClick = {
            viewModel.submitAction(UserUiAction.UserClick(it))
        }
    ) {
        viewModel.submitAction(UserUiAction.Search(it))
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserMainView(
    state: UiState<List<User>>,
    lazyPagingUserItems: LazyPagingItems<User>,
    onUserClick: (User) -> Unit,
    onValueChange: (String) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = stringResource(id = R.string.user_list_screen_title).uppercase(),
                    fontWeight = FontWeight.Bold
                )
            })
        },
        content = {
            Column(
                modifier = Modifier.padding(it),
            ) {
                SearchBox(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onValueChange = onValueChange,
                )
                UserSection(
                    state = state,
                    lazyPagingUserItems = lazyPagingUserItems,
                    onUserClick = onUserClick,
                )
            }
        }
    )
}

@Composable
fun UserSection(
    state: UiState<List<User>>,
    lazyPagingUserItems: LazyPagingItems<User>,
    onUserClick: (User) -> Unit,
) {
    when (lazyPagingUserItems.loadState.refresh) {
        is LoadState.Loading -> CustomCircularProgressIndicator()
        is LoadState.Error -> {} // TODO()
        is LoadState.NotLoading -> {} // TODO()
    }

    UserListContent(
        state = state,
        lazyPagingUserItems = lazyPagingUserItems,
        onUserClick = onUserClick,
    )
}

@Composable
fun UserListContent(
    state: UiState<List<User>>,
    lazyPagingUserItems: LazyPagingItems<User>,
    onUserClick: (User) -> Unit,
) {
    LazyColumn {
        items(items = lazyPagingUserItems) { user ->
            user?.let {
                UserListItem(it, onUserClick)
            }
        }

        // Load circular progress indicator when loading more elements
        if (lazyPagingUserItems.loadState.append == LoadState.Loading) {
            item {
                CustomCircularProgressIndicator()
            }
        }
    }
}

@Composable
fun UserListItem(
    user: User,
    onUserClick: (User) -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onUserClick(user)
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            CustomImage(
                url = user.picture,
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.size(16.dp))

            Column {
                Text(
                    text = user.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(0.9f)
                )

                Spacer(modifier = Modifier.size(6.dp))

                Text(
                    text = user.email,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.fillMaxWidth(0.9f)
                )
            }

            Spacer(modifier = Modifier.size(16.dp))

            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
            )
        }
        Divider(
            modifier = Modifier.padding(start = 88.dp)
        )
    }
}

@Preview(showBackground = false)
@Composable
fun UserListItemPreview() {
    UserListItem(user = User("email", "name", "url")) {}
}
