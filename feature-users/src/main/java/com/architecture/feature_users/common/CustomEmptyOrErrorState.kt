package com.architecture.feature_users.common

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.architecture.feature_users.R

@Composable
fun CustomEmptyOrErrorState(
    modifier: Modifier = Modifier,
    @DrawableRes drawableResId: Int? = null,
    @StringRes textResId: Int,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        drawableResId?.let {
            Image(
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .size(64.dp),
                painter = painterResource(id = drawableResId),
                contentScale = ContentScale.Inside,
                contentDescription = null
            )
        }

        Text(
            text = stringResource(textResId),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
            style = MaterialTheme.typography.headlineSmall,
        )
    }
}

@Preview("default", showBackground = false)
@Composable
fun EmptyOrErrorStatePreview() {
    CustomEmptyOrErrorState(
        drawableResId = R.drawable.ic_error,
        textResId = R.string.error_msg_no_connection
    )
}

@Preview("Only text", showBackground = false)
@Composable
fun EmptyOrErrorStateOnlyTextPreview() {
    CustomEmptyOrErrorState(textResId = R.string.error_msg_no_connection)
}
