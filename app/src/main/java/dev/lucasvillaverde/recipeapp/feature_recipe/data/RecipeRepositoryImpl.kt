package dev.lucasvillaverde.recipeapp.feature_recipe.data

import android.util.Log
import dev.lucasvillaverde.recipeapp.feature_recipe.data.local.dao.RecipeDao
import dev.lucasvillaverde.recipeapp.feature_recipe.data.local.entities.RecipeEntity
import dev.lucasvillaverde.recipeapp.feature_recipe.data.remote.services.RecipeService
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.repositories.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.job
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipeDao: RecipeDao,
    private val recipeService: RecipeService
) : RecipeRepository {
    override fun getRecipeById(id: Int): Flow<RecipeEntity> = recipeDao.getRecipeById(id)

    override fun getRecipes(): Flow<List<RecipeEntity>> = recipeDao.getRecipes()

    override suspend fun getNewRecipe() = withContext(Dispatchers.IO) {
        val meals = recipeService.getNewRecipe()
        recipeDao.insertAll(meals.recipeList)
    }


    override suspend fun deleteRecipes() = withContext(Dispatchers.IO) {
        recipeDao.deleteAllRecipes()
    }
}