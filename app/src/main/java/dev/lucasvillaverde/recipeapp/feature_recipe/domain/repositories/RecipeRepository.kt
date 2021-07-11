package dev.lucasvillaverde.recipeapp.feature_recipe.domain.repositories

import androidx.lifecycle.LiveData
import dev.lucasvillaverde.recipeapp.feature_recipe.data.local.entities.RecipeEntity

interface RecipeRepository {
    suspend fun refreshMeals()
    suspend fun deleteMeals()
    fun getMealById(id: Int): LiveData<RecipeEntity?>
    fun getMeals(): LiveData<List<RecipeEntity>>
}