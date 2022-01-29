package dev.lucasvillaverde.common.theme.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.lucasvillaverde.common.theme.RecipeAppTheme

@Composable
fun RecipeAppBar(
    modifier: Modifier = Modifier,
    screenName: String,
    onBackButtonClicked: () -> Unit,
    isTransparentStyle: Boolean
) {
    if (isTransparentStyle) {
        TopAppBar(
            modifier = modifier,
            title = {
                Text(
                    color = MaterialTheme.colors.onPrimary,
                    text = screenName
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackButtonClicked) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            backgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.5F),
            elevation = 0.dp
        )

        return
    }

    TopAppBar(
        modifier = modifier,
        title = { Text(screenName) },
        navigationIcon = {
            IconButton(onClick = onBackButtonClicked) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        backgroundColor = MaterialTheme.colors.primary
    )
}

@Preview
@Composable
fun RecipeAppBarPreview() {
    RecipeAppTheme {
        RecipeAppBar(
            screenName = "Screen Test",
            onBackButtonClicked = {},
            isTransparentStyle = true
        )
    }
}