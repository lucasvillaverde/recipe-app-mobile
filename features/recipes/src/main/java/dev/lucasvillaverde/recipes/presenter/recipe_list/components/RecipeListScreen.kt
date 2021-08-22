package dev.lucasvillaverde.recipes.presenter.recipe_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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

    if (recipeListPageState.isLoading) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxHeight()
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(24.dp)
            )
        }

        return
    }

    if (recipeListPageState.data.isNullOrEmpty()) {
        RecipeEmptyList()

        return
    }

    RecipeList(
        recipeListPageState.data!!,
        onRecipeClick = onRecipeClick,
        onFavoriteClick = { recipeId ->
            recipeListViewModel.favoriteRecipe(recipeId)
        }
    )
}