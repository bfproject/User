package com.architecture.feature_users.list

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.architecture.feature_users.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBox(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
) {
    var textValue by rememberSaveable { mutableStateOf("") }
    TextField(
        modifier = modifier,
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = stringResource(id = R.string.search_box_content_description)
            )
        },
        singleLine = true,
        placeholder = {
            Text(
                text = stringResource(id = R.string.search_box_placeholder)
            )
        },
        value = textValue,
        onValueChange = {
            textValue = it
            onValueChange(it)
        },
    )
}

@Preview
@Composable
fun PreviewSearchBox() {
    SearchBox(onValueChange = { })
}
