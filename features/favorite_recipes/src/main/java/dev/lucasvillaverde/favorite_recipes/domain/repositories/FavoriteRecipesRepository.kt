package dev.lucasvillaverde.favorite_recipes.domain.repositories

import dev.lucasvillaverde.recipeapp.core.data.local.model.RecipeEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteRecipesRepository {
    fun getFavoriteRecipes(): Flow<List<RecipeEntity>>
    suspend fun removeRecipeFromFavorite(id: Int)
}