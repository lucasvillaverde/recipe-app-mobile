package dev.lucasvillaverde.recipeapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.lucasvillaverde.recipeapp.feature_recipe.data.local.entities.MealEntity
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.repositories.MealRepository

class MealRepositoryTestImpl : MealRepository {
    override suspend fun refreshMeals() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMeals() {
        TODO("Not yet implemented")
    }

    override fun getMealById(id: Int): LiveData<MealEntity?> {
        TODO("Not yet implemented")
    }

    override fun getMeals(): LiveData<List<MealEntity>> = MutableLiveData(
        mutableListOf(
            MealEntity(
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