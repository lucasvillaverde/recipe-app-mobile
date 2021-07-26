package dev.lucasvillaverde.common.base.presenter

import android.os.Bundle

sealed class NavDirection(val bundle: Bundle?) {
    class FavoriteRecipesToRecipeDetails(bundle: Bundle? = null) : NavDirection(bundle) {
        companion object {
            const val recipeIdArgName = "recipe_id"
        }
    }

    class RecipeListToFavoriteRecipes(bundle: Bundle? = null) : NavDirection(bundle)
    class RecipeListToRecipeDetails(bundle: Bundle? = null) : NavDirection(bundle) {
        companion object {
            const val recipeIdArgName = "recipe_id"
        }
    }
}
