package dev.lucasvillaverde.recipes.presenter.recipe_list.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
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

@ExperimentalComposeUiApi
@Composable
fun SearchBar(
    searchText: String,
    onSearchTextChanged: (text: String) -> Unit
) {
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
                    .padding(horizontal = 8.dp),
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
                value = searchText,
                placeholder = { Text("Search your favorite meal!") },
                onValueChange = onSearchTextChanged,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        //implementar func
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
    var searchText by rememberSaveable { mutableStateOf("") }

    SearchBar(
        searchText = searchText,
        onSearchTextChanged = {
            searchText = it
        }
    )
}