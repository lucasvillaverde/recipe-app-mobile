package dev.lucasvillaverde.favorite_recipes.presenter.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import dev.lucasvillaverde.favorite_recipes.domain.model.FavoriteRecipe

@Composable
fun FavoriteRecipeListItem(
    favoriteRecipe: FavoriteRecipe,
    onFavoriteRecipeClick: (id: Int) -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth()
            .clickable(onClick = { onFavoriteRecipeClick(favoriteRecipe.id) }),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
            painter = rememberAsyncImagePainter(model = favoriteRecipe.photoUrl),
            contentDescription = "A ${favoriteRecipe.name} recipe photo"
        )
        Column(
            modifier = Modifier
                .weight(1.0f)
                .padding(horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                favoriteRecipe.name,
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Center
            )
            Text(
                favoriteRecipe.category,
                style = MaterialTheme.typography.caption,
                textAlign = TextAlign.Center
            )
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