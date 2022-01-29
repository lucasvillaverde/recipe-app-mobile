package dev.lucasvillaverde.recipes.presenter.recipe_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import dev.lucasvillaverde.common.base.model.BasePageState
import dev.lucasvillaverde.recipes.domain.model.RecipeModel

@Composable
fun RecipeListContent(
    pageState: BasePageState<List<RecipeModel>>,
    filterList: (String) -> Unit,
    fetchList: () -> Unit,
    favoriteRecipe: (Int) -> Unit,
    onRecipeClick: (Int) -> Unit,
    onAddNewRecipe: () -> Unit,
    deleteRecipes: () -> Unit,
    onFavoriteScreenButtonClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize()
    ) {
        SearchBar(
            onSearchAction = { queryText ->
                filterList(queryText)
            }
        )

        Row(
            modifier = Modifier.weight(weight = 1F)
        ) {
            when {
                pageState.isLoading -> Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(24.dp)
                    )
                }
                pageState.data.isNullOrEmpty() -> RecipeEmptyList()
                else -> SwipeRefresh(
                    state = rememberSwipeRefreshState(pageState.isLoading),
                    onRefresh = { fetchList() }
                ) {
                    RecipeList(
                        pageState.data!!,
                        onRecipeClick = onRecipeClick,
                        onFavoriteClick = { recipeId ->
                            favoriteRecipe(recipeId)
                        }
                    )
                }
            }
        }

        BottomBar(
            onAddNewRecipe = onAddNewRecipe,
            onDeleteRecipes = deleteRecipes,
            onFavoriteScreenButtonClick = onFavoriteScreenButtonClick
        )
    }
}

@Preview
@Composable
fun RecipeListContentPreview() {
    RecipeListContent(
        pageState = BasePageState(
            data = emptyList()
        ),
        filterList = {},
        fetchList = {},
        favoriteRecipe = {},
        onRecipeClick = {},
        onAddNewRecipe = {},
        deleteRecipes = {},
        onFavoriteScreenButtonClick = {}
    )
}