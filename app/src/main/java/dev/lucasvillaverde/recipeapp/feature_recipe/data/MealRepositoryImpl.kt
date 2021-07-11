package dev.lucasvillaverde.recipeapp.feature_recipe.data

import androidx.lifecycle.LiveData
import dev.lucasvillaverde.recipeapp.feature_recipe.data.local.dao.MealDAO
import dev.lucasvillaverde.recipeapp.feature_recipe.data.local.entities.MealEntity
import dev.lucasvillaverde.recipeapp.feature_recipe.data.remote.services.MealService
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.repositories.MealRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MealRepositoryImpl @Inject constructor(
    private val mealDAO: MealDAO,
    private val mealService: MealService
) : MealRepository {
    override fun getMealById(id: Int): LiveData<MealEntity?> = mealDAO.getMealById(id)

    override fun getMeals(): LiveData<List<MealEntity>> = mealDAO.getMeals()

    override suspend fun refreshMeals() {
        withContext(Dispatchers.IO) {
            val meals = mealService.getMeals()
            mealDAO.insertAll(meals.mealList)
        }
    }

    override suspend fun deleteMeals() {
        withContext(Dispatchers.IO) {
            mealDAO.deleteAllMeals()
        }
    }
}