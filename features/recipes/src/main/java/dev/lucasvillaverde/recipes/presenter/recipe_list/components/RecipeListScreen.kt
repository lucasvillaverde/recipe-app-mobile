package dev.lucasvillaverde.recipes.presenter.recipe_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import dev.lucasvillaverde.common.base.model.BasePageState
import dev.lucasvillaverde.recipes.presenter.recipe_list.RecipeListViewModel

@Composable
fun RecipeListScreen(
    onRecipeClick: (recipeId: Int) -> Unit,
    recipeListViewModel: RecipeListViewModel = viewModel()
) {
    LaunchedEffect(key1 = "recipeListPageState", block = {
        recipeListViewModel.fetchRecipeList()
    })

    val recipeListPageState by recipeListViewModel.pageState.observeAsState(
        BasePageState(
            isLoading = false,
            isError = false,
            data = listOf()
        )
    )

    when {
        recipeListPageState.isLoading -> Row(
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
        recipeListPageState.data.isNullOrEmpty() -> RecipeEmptyList()
        else -> SwipeRefresh(
            state = rememberSwipeRefreshState(recipeListPageState.isLoading),
            onRefresh = { recipeListViewModel.fetchRecipeList() }
        ) {
            RecipeList(
                recipeListPageState.data!!,
                onRecipeClick = onRecipeClick,
                onFavoriteClick = { recipeId ->
                    recipeListViewModel.favoriteRecipe(recipeId)
                }
            )
        }
    }
}