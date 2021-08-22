package dev.lucasvillaverde.recipes.presenter.recipe_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.lucasvillaverde.recipes.R

@Composable
fun RecipeEmptyList() {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.padding(24.dp),
            painter = painterResource(id = R.drawable.ic_find_your_meal),
            contentDescription = "find your meal image"
        )
        Text(
            text = stringResource(id = R.string.empty_state_swipe_down),
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun RecipeEmptyListPreview() {
    RecipeEmptyList()
}