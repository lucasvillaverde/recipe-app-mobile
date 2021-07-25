package dev.lucasvillaverde.recipeapp.feature_recipe.data

import dev.lucasvillaverde.common.core.local.model.RecipeEntity
import dev.lucasvillaverde.common.core.local.dao.RecipeDao
import dev.lucasvillaverde.recipeapp.feature_recipe.data.remote.model.toEntity
import dev.lucasvillaverde.recipeapp.feature_recipe.data.remote.services.RecipeService
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.repositories.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipeRepositoryImpl(
    private val recipeDao: RecipeDao,
    private val recipeService: RecipeService
) : RecipeRepository {
    override suspend fun getRecipeById(id: Int): RecipeEntity = withContext(Dispatchers.IO) {
        recipeDao.getRecipeById(id)
    }

    override suspend fun getRecipes(): List<RecipeEntity> = withContext(Dispatchers.IO) {
        recipeDao.getRecipes()
    }

    override suspend fun toggleRecipeIsFavorite(id: Int) = withContext(Dispatchers.IO) {
        recipeDao.toggleRecipeIsFavorite(id)
    }

    override suspend fun fetchNewRecipe() = withContext(Dispatchers.IO) {
        val meals = recipeService.getNewRecipe()
        recipeDao.insertAll(meals.recipeList.map { it.toEntity() })
    }

    override suspend fun deleteRecipes() = withContext(Dispatchers.IO) {
        recipeDao.deleteAllRecipes()
    }
}