package dev.lucasvillaverde.recipes.presenter.recipe_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.lucasvillaverde.common.theme.RecipeAppTheme
import dev.lucasvillaverde.recipes.R
import dev.lucasvillaverde.recipes.domain.model.RecipeModel

@Composable
fun RecipeDetailsContent(
    recipeModel: RecipeModel,
    onFavoriteRecipe: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            modifier = Modifier
                .height(140.dp),
            model = recipeModel.thumb,
            contentScale = ContentScale.Crop,
            contentDescription = "Meal Image",
            loading = {
                CircularProgressIndicator(
                    modifier = Modifier.size(80.dp)
                )
            }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier.clickable {
                    onFavoriteRecipe(recipeModel.id)
                },
                painter = painterResource(
                    id =
                    if (recipeModel.isFavorite) R.drawable.ic_baseline_favorite_24
                    else R.drawable.ic_baseline_favorite_border_24
                ),
                tint = Color.Red,
                contentDescription = "Favorite Recipe"
            )
        }

        RecipeDetailsTab(recipeModel = recipeModel)
    }
}

@Preview
@Composable
fun RecipeDetailsContentPreview() {
    RecipeAppTheme {
        RecipeDetailsContent(
            recipeModel = RecipeModel(
                id = 0,
                tags = null,
                youtubeLink = null,
                name = "Test Recipe",
                category = "Dessert",
                region = "Test Region",
                instructions = "Test Instructions",
                thumb = null,
                ingredientsMeasures = mapOf(),
                isFavorite = false
            ),
            onFavoriteRecipe = {}
        )
    }
}