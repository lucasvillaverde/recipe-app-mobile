package dev.lucasvillaverde.recipes.presenter.recipe_list.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.lucasvillaverde.recipes.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    onSearchAction: (text: String) -> Unit
) {
    var queryText by remember { mutableStateOf("") }
    var isFieldFocused by remember { mutableStateOf(false) }
    val keyboard = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Row(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Surface(
            color = MaterialTheme.colors.primary
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .onFocusChanged {
                        isFieldFocused = it.isFocused
                    },
                leadingIcon = {
                    Icon(
                        tint = MaterialTheme.colors.secondary,
                        painter = painterResource(id = R.drawable.ic_baseline_search_24),
                        contentDescription = "Search Icon"
                    )
                },
                textStyle = TextStyle(
                    fontSize = 18.sp
                ),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    cursorColor = MaterialTheme.colors.secondaryVariant
                ),
                maxLines = 1,
                value = queryText,
                placeholder = {
                    if (isFieldFocused) {
                        return@TextField
                    }

                    Text("Search your favorite meal!")
                },
                onValueChange = {
                    queryText = it
                    onSearchAction.invoke(queryText)
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearchAction.invoke(queryText)
                        keyboard?.hide()
                        focusManager.clearFocus()
                    }
                )
            )
        }
    }
}


@ExperimentalComposeUiApi
@Preview
@Composable
fun SearchBarPreview() {
    SearchBar(
        onSearchAction = {}
    )
}