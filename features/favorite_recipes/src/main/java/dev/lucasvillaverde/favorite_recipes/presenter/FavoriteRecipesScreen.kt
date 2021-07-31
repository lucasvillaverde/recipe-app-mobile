package dev.lucasvillaverde.favorite_recipes.presenter

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.lucasvillaverde.common.base.model.BaseResource
import dev.lucasvillaverde.favorite_recipes.presenter.components.FavoriteRecipeList

@ExperimentalFoundationApi
@Composable
fun FavoriteRecipeScreen(
    onFavoriteRecipeClick: (id: Int) -> Unit,
    favoriteRecipesViewModel: FavoriteRecipesViewModel = viewModel()
) {
    val favoriteRecipeResource = favoriteRecipesViewModel.favoriteRecipes.observeAsState(
        BaseResource.Success(
            listOf()
        )
    )

    MaterialTheme {
        favoriteRecipeResource.value.let {
            when (it) {
                is BaseResource.Success -> FavoriteRecipeList(
                    favoriteRecipes = it.data!!,
                    onFavoriteRecipeClick
                )
                else -> ""
            }
        }
    }
}