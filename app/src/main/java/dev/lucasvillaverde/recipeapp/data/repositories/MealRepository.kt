package dev.lucasvillaverde.recipeapp.data.repositories

import androidx.lifecycle.LiveData
import dev.lucasvillaverde.recipeapp.data.local.MealDatabase
import dev.lucasvillaverde.recipeapp.data.local.dao.MealDAO
import dev.lucasvillaverde.recipeapp.data.local.entities.MealEntity
import dev.lucasvillaverde.recipeapp.data.remote.services.MealService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MealRepository @Inject constructor(private val mealDAO: MealDAO, private val mealService: MealService) {

    val meals: LiveData<List<MealEntity>> = mealDAO.getMeals()

    suspend fun refreshMeals() {
        withContext(Dispatchers.IO) {
            val meals = mealService.getRandomMealData()
            mealDAO.insertAll(meals.mealList)
        }
    }

    fun getMealById(id: Int): LiveData<MealEntity?> = mealDAO.getMealById(id)

    suspend fun deleteMeals() {
        withContext(Dispatchers.IO) {
            mealDAO.deleteAllMeals()
        }
    }

}