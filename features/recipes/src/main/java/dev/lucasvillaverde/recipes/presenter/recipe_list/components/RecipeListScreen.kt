package dev.lucasvillaverde.recipes.presenter.recipe_list.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.lucasvillaverde.common.base.model.BasePageState
import dev.lucasvillaverde.recipes.presenter.recipe_list.RecipeListViewModel

@Composable
fun RecipeListScreen(
    onRecipeClick: (recipeId: Int) -> Unit,
    recipeListViewModel: RecipeListViewModel = viewModel()
) {

    val recipeListPageState by recipeListViewModel.pageState.observeAsState(
        BasePageState(
            isLoading = false,
            isError = false,
            data = listOf()
        )
    )

    if (recipeListPageState.data.isNullOrEmpty()) {
        RecipeEmptyList()

        return
    }

    RecipeList(
        recipeListPageState.data!!,
        onRecipeClick = onRecipeClick,
        onFavoriteClick = {
            // TODO: 19/08/2021 - Implements FavoriteRecipeUseCase
        }
    )
}