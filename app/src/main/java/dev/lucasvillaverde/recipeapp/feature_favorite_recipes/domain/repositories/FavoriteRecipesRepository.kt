package dev.lucasvillaverde.recipeapp.feature_favorite_recipes.domain.repositories

import dev.lucasvillaverde.recipeapp.core.data.local.model.RecipeEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteRecipesRepository {
    fun getFavoriteRecipes(): List<RecipeEntity>
    fun getFavoriteRecipesFlow(): Flow<List<RecipeEntity>>
    suspend fun removeRecipeFromFavorite(id: Int)
}