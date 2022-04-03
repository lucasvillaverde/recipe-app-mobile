package dev.lucasvillaverde.recipes.presenter.recipe_details.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import dev.lucasvillaverde.common.base.model.BasePageState
import dev.lucasvillaverde.recipes.presenter.recipe_details.RecipeDetailsViewModel

@Composable
fun RecipeDetailsScreen(
    recipeId: Int,
    onBackPressed: () -> Unit,
    recipeDetailsViewModel: RecipeDetailsViewModel
) {
    LaunchedEffect(key1 = "recipe_loading", block = {
        recipeDetailsViewModel.getRecipe(recipeId)
    })

    val recipeDetailsState by recipeDetailsViewModel.pageState.observeAsState(
        BasePageState(data = null)
    )

    recipeDetailsState.data?.let {
        RecipeDetailsContent(
            recipeModel = it,
            onFavoriteRecipe = { recipeId ->
                recipeDetailsViewModel.toggleRecipeIsFavorite(recipeId)
            },
            onBackPressed = onBackPressed
        )
    }
}