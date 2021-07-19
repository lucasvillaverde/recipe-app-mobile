package dev.lucasvillaverde.recipeapp.feature_favorite_recipes.domain.repositories

import dev.lucasvillaverde.recipeapp.core.data.local.model.RecipeEntity

interface FavoriteRecipesRepository {
    suspend fun getFavoriteRecipes(): List<RecipeEntity>
    suspend fun removeRecipeFromFavorite(id: Int)
}