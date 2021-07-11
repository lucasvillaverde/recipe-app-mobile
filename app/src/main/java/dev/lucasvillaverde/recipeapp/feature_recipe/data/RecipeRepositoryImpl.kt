package dev.lucasvillaverde.recipeapp.feature_recipe.data

import androidx.lifecycle.LiveData
import dev.lucasvillaverde.recipeapp.feature_recipe.data.local.dao.RecipeDao
import dev.lucasvillaverde.recipeapp.feature_recipe.data.local.entities.RecipeEntity
import dev.lucasvillaverde.recipeapp.feature_recipe.data.remote.services.RecipeService
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.repositories.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipeDao: RecipeDao,
    private val recipeService: RecipeService
) : RecipeRepository {
    override fun getMealById(id: Int): LiveData<RecipeEntity?> = recipeDao.getMealById(id)

    override fun getMeals(): LiveData<List<RecipeEntity>> = recipeDao.getMeals()

    override suspend fun refreshMeals() {
        withContext(Dispatchers.IO) {
            val meals = recipeService.getMeals()
            recipeDao.insertAll(meals.recipeList)
        }
    }

    override suspend fun deleteMeals() {
        withContext(Dispatchers.IO) {
            recipeDao.deleteAllMeals()
        }
    }
}