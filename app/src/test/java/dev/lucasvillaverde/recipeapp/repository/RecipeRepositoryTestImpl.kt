package dev.lucasvillaverde.recipeapp.repository

import dev.lucasvillaverde.recipeapp.feature_recipe.data.local.model.RecipeEntity
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.repositories.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipeRepositoryTestImpl : RecipeRepository {
    override suspend fun fetchNewRecipe() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteRecipes() {
        TODO("Not yet implemented")
    }

    override suspend fun getRecipeById(id: Int): RecipeEntity = withContext(Dispatchers.IO) {
        RecipeEntity(
            1,
            "Teste Recipe Entity",
            "Test Category",
            "Region Test",
            "Test Instructions",
            null,
            null,
            null,
            mapOf(
                Pair("Sweet potato", "2 pieces"),
                Pair("Water", "2 cups")
            )
        )
    }

    override suspend fun getRecipes(): List<RecipeEntity> = withContext(Dispatchers.IO) {
        mutableListOf(
            RecipeEntity(
                1,
                "Teste",
                "Test Category",
                "Region Test",
                "Test Instructions",
                null,
                null,
                null,
                mapOf(
                    Pair("Sweet potato", "2 pieces"),
                    Pair("Water", "2 cups")
                )
            )
        )
    }
}