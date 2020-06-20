package dev.lucasvillaverde.recipeapp.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.lucasvillaverde.recipeapp.data.Network
import dev.lucasvillaverde.recipeapp.data.local.MealDatabase
import dev.lucasvillaverde.recipeapp.data.local.entities.MealEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MealRepository(private val mealDatabase: MealDatabase) {

    val meals: LiveData<List<MealEntity>> = mealDatabase.mealDAO.getMeals()

    suspend fun refreshMeals() {
        withContext(Dispatchers.IO) {
            val meals = Network.mealService.getRandomMealData()
            mealDatabase.mealDAO.insertAll(meals.mealList)
        }
    }

    fun getMealById(id: Int): LiveData<MealEntity?> = mealDatabase.mealDAO.getMealById(id)

    suspend fun deleteMeals() {
        withContext(Dispatchers.IO) {
            mealDatabase.mealDAO.deleteAllMeals()
        }
    }

}