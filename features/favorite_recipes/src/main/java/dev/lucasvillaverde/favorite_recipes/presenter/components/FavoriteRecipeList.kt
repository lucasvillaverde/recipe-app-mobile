package dev.lucasvillaverde.favorite_recipes.presenter.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.lucasvillaverde.favorite_recipes.domain.model.FavoriteRecipe

@ExperimentalFoundationApi
@Composable
fun FavoriteRecipeList(
    favoriteRecipes: List<FavoriteRecipe>,
    onFavoriteRecipeClick: (id: Int) -> Unit
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxHeight()
            .padding(vertical = 12.dp, horizontal = 8.dp)
    ) {
        items(favoriteRecipes) { recipe ->
            FavoriteRecipeListItem(recipe, onFavoriteRecipeClick)
        }
    }
}

@ExperimentalFoundationApi
@Preview
@Composable
fun FavoriteRecipeListPreview() {
    FavoriteRecipeList(
        favoriteRecipes = listOf(
            FavoriteRecipe(
                id = 1,
                name = "Test Recipe",
                category = "Testing",
                photoUrl = ""
            ),
            FavoriteRecipe(
                id = 2,
                name = "Test Recipe 2",
                category = "Testing 2",
                photoUrl = ""
            ),
            FavoriteRecipe(
                id = 3,
                name = "Test Recipe 3",
                category = "Testing 3",
                photoUrl = ""
            )
        ),
        onFavoriteRecipeClick = {}
    )
}