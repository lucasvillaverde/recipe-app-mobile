package dev.lucasvillaverde.recipeapp.feature_recipe.domain.repositories

import androidx.lifecycle.LiveData
import dev.lucasvillaverde.recipeapp.feature_recipe.data.local.entities.MealEntity

interface MealRepository {
    suspend fun refreshMeals()
    suspend fun deleteMeals()
    fun getMealById(id: Int): LiveData<MealEntity?>
    fun getMeals(): LiveData<List<MealEntity>>
}