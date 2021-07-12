package dev.lucasvillaverde.recipeapp.feature_recipe.domain.repositories

import dev.lucasvillaverde.recipeapp.feature_recipe.data.local.entities.RecipeEntity
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    suspend fun getNewRecipe()
    suspend fun deleteRecipes()
    fun getRecipeById(id: Int): Flow<RecipeEntity>
    suspend fun getRecipes(): List<RecipeEntity>
}