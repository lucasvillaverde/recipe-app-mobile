package dev.lucasvillaverde.favorite_recipes.presenter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.lucasvillaverde.common.base.model.BaseResource
import dev.lucasvillaverde.common.theme.components.RecipeAppBar
import dev.lucasvillaverde.favorite_recipes.domain.model.FavoriteRecipe
import dev.lucasvillaverde.favorite_recipes.presenter.components.FavoriteRecipeList

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FavoriteRecipeContent(
    favoriteRecipeState: BaseResource<List<FavoriteRecipe>>,
    onFavoriteRecipeClick: (Int) -> Unit,
    onRemoveFavoriteRecipe: (Int) -> Unit,
    onBackPressed: () -> Unit

) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        RecipeAppBar(
            screenName = "Favorite Recipes",
            onBackButtonClicked = onBackPressed,
            isTransparentStyle = false
        )
        when (favoriteRecipeState) {
            is BaseResource.Success -> if (!favoriteRecipeState.data.isNullOrEmpty()) {
                FavoriteRecipeList(
                    favoriteRecipes = favoriteRecipeState.data!!,
                    onFavoriteRecipeClick = onFavoriteRecipeClick,
                    onRemoveFavoriteRecipeClick = { recipeId ->
                        onRemoveFavoriteRecipe.invoke(recipeId)
                    }
                )
            } else {
                Text("There's no favorite recipe. Favorite one by clicking on the heart icon :)")
            }
            else -> TODO("NOT IMPLEMENTED YET")
        }
    }
}

@Preview
@Composable
fun FavoriteRecipeContentPreview() {
    FavoriteRecipeContent(
        favoriteRecipeState = BaseResource.Success(emptyList()),
        onFavoriteRecipeClick = {},
        onRemoveFavoriteRecipe = {},
        onBackPressed = {}
    )
}