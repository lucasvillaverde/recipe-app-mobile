package dev.lucasvillaverde.recipes.data

import dev.lucasvillaverde.common.core.local.dao.RecipeDao
import dev.lucasvillaverde.common.core.local.model.RecipeEntity
import dev.lucasvillaverde.recipes.data.remote.model.toEntity
import dev.lucasvillaverde.recipes.data.remote.services.RecipeService
import dev.lucasvillaverde.recipes.domain.repositories.RecipeRepository
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