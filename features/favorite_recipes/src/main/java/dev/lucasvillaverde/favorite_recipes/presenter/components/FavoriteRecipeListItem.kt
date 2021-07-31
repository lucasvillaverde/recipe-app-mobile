package dev.lucasvillaverde.favorite_recipes.presenter.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import dev.lucasvillaverde.favorite_recipes.domain.model.FavoriteRecipe

@Composable
fun FavoriteRecipeListItem(
    favoriteRecipe: FavoriteRecipe,
    onFavoriteRecipeClick: (id: Int) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(IntrinsicSize.Max)
            .padding(vertical = 8.dp)
            .clickable {
                onFavoriteRecipeClick(favoriteRecipe.id)
            }
    ) {
        Row {
            Image(
                painter = rememberImagePainter(favoriteRecipe.photoUrl),
                contentDescription = "Recipe Photo",
                Modifier.size(90.dp)
            )
        }

        Row {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(favoriteRecipe.name, textAlign = TextAlign.Center)
                Text(favoriteRecipe.category, textAlign = TextAlign.Center)
            }
        }
    }
}

@Preview
@Composable
fun FavoriteRecipeListItemPreview() {
    FavoriteRecipeListItem(
        favoriteRecipe = FavoriteRecipe(
            id = 0,
            name = "Test Recipe",
            category = "Testing",
            photoUrl = ""
        ),
        onFavoriteRecipeClick = {}
    )
}