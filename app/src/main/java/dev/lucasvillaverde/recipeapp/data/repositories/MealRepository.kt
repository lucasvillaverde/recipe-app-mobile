package dev.lucasvillaverde.recipeapp.data.repositories

import androidx.lifecycle.LiveData
import dev.lucasvillaverde.recipeapp.data.local.entities.MealEntity
import kotlinx.coroutines.Job

interface MealRepository {
    suspend fun refreshMeals()
    suspend fun deleteMeals()
    fun getMealById(id: Int): LiveData<MealEntity?>
    fun getMeals(): LiveData<List<MealEntity>>
}