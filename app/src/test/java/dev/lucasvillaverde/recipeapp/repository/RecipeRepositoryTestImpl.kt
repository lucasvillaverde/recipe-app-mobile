package dev.lucasvillaverde.recipeapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.lucasvillaverde.recipeapp.feature_recipe.data.local.entities.RecipeEntity
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.repositories.RecipeRepository

class RecipeRepositoryTestImpl : RecipeRepository {
    override suspend fun getNewRecipe() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteRecipes() {
        TODO("Not yet implemented")
    }

    override fun getMealById(id: Int): LiveData<RecipeEntity?> {
        TODO("Not yet implemented")
    }

    override fun getMeals(): LiveData<List<RecipeEntity>> = MutableLiveData(
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
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
            )
        )
    )
}