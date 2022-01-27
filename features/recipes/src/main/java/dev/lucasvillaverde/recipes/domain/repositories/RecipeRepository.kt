package dev.lucasvillaverde.recipes.domain.repositories

import dev.lucasvillaverde.common.core.local.model.RecipeEntity

interface RecipeRepository {
    suspend fun fetchNewRecipe()
    suspend fun deleteRecipes()
    suspend fun getRecipeById(id: Int): RecipeEntity
    suspend fun getRecipes(): List<RecipeEntity>
    suspend fun getRecipesByName(recipeName: String): List<RecipeEntity>
    suspend fun toggleRecipeIsFavorite(id: Int)
}