package dev.lucasvillaverde.recipeapp.feature_recipe.domain.repositories

import dev.lucasvillaverde.recipeapp.feature_recipe.data.local.model.RecipeEntity

interface RecipeRepository {
    suspend fun fetchNewRecipe()
    suspend fun deleteRecipes()
    suspend fun getRecipeById(id: Int): RecipeEntity
    suspend fun getRecipes(): List<RecipeEntity>
}