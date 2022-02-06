package dev.lucasvillaverde.favorite_recipes.presenter

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.lucasvillaverde.common.base.model.BaseResource
import dev.lucasvillaverde.favorite_recipes.domain.model.FavoriteRecipe
import dev.lucasvillaverde.favorite_recipes.presenter.components.FavoriteRecipeList

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FavoriteRecipeContent(
    favoriteRecipeState: BaseResource<List<FavoriteRecipe>>,
    onFavoriteRecipeClick: (Int) -> Unit,
    onRemoveFavoriteRecipe: (Int) -> Unit

) {
    when (favoriteRecipeState) {
        is BaseResource.Success -> FavoriteRecipeList(
            favoriteRecipes = favoriteRecipeState.data!!,
            onFavoriteRecipeClick = onFavoriteRecipeClick,
            onRemoveFavoriteRecipeClick = { recipeId ->
                onRemoveFavoriteRecipe.invoke(recipeId)
            }
        )
        else -> TODO("NOT IMPLEMENTED YET")
    }
}

@Preview
@Composable
fun FavoriteRecipeContentPreview() {
    FavoriteRecipeContent(
        favoriteRecipeState = BaseResource.Success(emptyList()),
        onFavoriteRecipeClick = {},
        onRemoveFavoriteRecipe = {}
    )
}