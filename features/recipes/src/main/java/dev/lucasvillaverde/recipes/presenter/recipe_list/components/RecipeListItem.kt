package dev.lucasvillaverde.recipes.presenter.recipe_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import dev.lucasvillaverde.recipes.R
import dev.lucasvillaverde.recipes.domain.model.RecipeModel

@Composable
fun RecipeListItem(
    recipe: RecipeModel,
    onRecipeClick: (recipeId: Int) -> Unit,
    onFavoriteClick: (recipeId: Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onRecipeClick(recipe.id)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape),
            painter = rememberImagePainter(recipe.thumb),
            contentDescription = recipe.name
        )
        Column(
            modifier = Modifier
                .weight(1.0f)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = recipe.name,
                textAlign = TextAlign.Center
            )
            Text(
                text = recipe.category,
                style = MaterialTheme.typography.caption
            )
        }
        IconButton(
            onClick = { onFavoriteClick(recipe.id) },
        ) {
            Icon(
                painter = painterResource(
                    if (recipe.isFavorite) R.drawable.ic_baseline_favorite_24
                    else R.drawable.ic_baseline_favorite_border_24
                ),
                contentDescription = "favorite"
            )
        }
    }
}


@Preview
@Composable
fun RecipeListItemPreview() {
    RecipeListItem(
        RecipeModel(
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
        onRecipeClick = {},
        onFavoriteClick = {}
    )
}