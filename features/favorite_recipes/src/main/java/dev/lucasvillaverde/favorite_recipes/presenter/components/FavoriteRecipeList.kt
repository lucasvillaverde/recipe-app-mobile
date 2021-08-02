package dev.lucasvillaverde.favorite_recipes.presenter.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.lucasvillaverde.favorite_recipes.domain.model.FavoriteRecipe

@Composable
fun FavoriteRecipeList(
    favoriteRecipes: List<FavoriteRecipe>,
    onFavoriteRecipeClick: (id: Int) -> Unit,
    onRemoveFavoriteRecipeClick: (id: Int) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 12.dp, horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(
            key = { recipe -> recipe.id },
            items = favoriteRecipes
        ) { recipe ->
            FavoriteRecipeListItem(
                favoriteRecipe = recipe,
                onFavoriteRecipeClick = onFavoriteRecipeClick,
                onRemoveFavoriteRecipeClick = onRemoveFavoriteRecipeClick
            )
            Spacer(Modifier.height(12.dp))

            if (recipe.hashCode() != favoriteRecipes.last().hashCode()) {
                Divider(color = Color(0xFFEBEBEB))
            }
        }
    }
}

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
        onFavoriteRecipeClick = {},
        onRemoveFavoriteRecipeClick = {}
    )
}