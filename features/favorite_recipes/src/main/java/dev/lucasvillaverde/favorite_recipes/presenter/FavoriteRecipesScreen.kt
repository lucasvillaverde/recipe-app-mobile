package dev.lucasvillaverde.favorite_recipes.presenter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.lucasvillaverde.common.base.model.BaseResource

@Composable
fun FavoriteRecipeScreen(
    onFavoriteRecipeClick: (id: Int) -> Unit,
    favoriteRecipesViewModel: FavoriteRecipesViewModel = viewModel()
) {
    val favoriteRecipeResource by favoriteRecipesViewModel.favoriteRecipes.observeAsState(
        BaseResource.Success(
            listOf()
        )
    )

    favoriteRecipeResource.data?.let {
        FavoriteRecipeContent(
            favoriteRecipeState = favoriteRecipeResource,
            onFavoriteRecipeClick = onFavoriteRecipeClick,
            onRemoveFavoriteRecipe = { favoriteRecipesViewModel.removeFromFavorite(it) }
        )
    }
}