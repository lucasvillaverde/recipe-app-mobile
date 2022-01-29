package dev.lucasvillaverde.recipes.presenter.recipe_list.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.lucasvillaverde.common.theme.RecipeAppTheme
import dev.lucasvillaverde.recipes.R

@Composable
fun BottomBar(
    onAddNewRecipe: () -> Unit,
    onDeleteRecipes: () -> Unit,
    onFavoriteScreenButtonClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(horizontal = 8.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Button(
            modifier = Modifier.weight(1F),
            onClick = onAddNewRecipe,
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Recipe Icon"
                )
                Text(
                    textAlign = TextAlign.Center,
                    text = "Add Recipe"
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        Button(
            modifier = Modifier.weight(1F),
            onClick = onDeleteRecipes
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_delete_forever_24),
                    contentDescription = "Delete Recipes Icon"
                )
                Text(
                    textAlign = TextAlign.Center,
                    text = "Delete Recipes"
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        Button(
            modifier = Modifier.weight(1F),
            onClick = onFavoriteScreenButtonClick
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_favorite_24),
                    contentDescription = "Favorite Recipes Screen Icon"
                )
                Text(
                    textAlign = TextAlign.Center,
                    text = "Favorite Screen"
                )
            }
        }
    }
}

@Preview
@Composable
fun BottomBarPreview() {
    RecipeAppTheme {
        BottomBar(
            onAddNewRecipe = {},
            onDeleteRecipes = {},
            onFavoriteScreenButtonClick = {}
        )
    }
}