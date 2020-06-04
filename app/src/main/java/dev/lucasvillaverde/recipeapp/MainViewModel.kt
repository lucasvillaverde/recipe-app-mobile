package dev.lucasvillaverde.recipeapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dev.lucasvillaverde.recipeapp.data.local.entities.MealEntity
import dev.lucasvillaverde.recipeapp.data.repositories.MealRepository

class MainViewModel : ViewModel() {

    val mealRepository = MealRepository()

    fun getRandomMeal(): LiveData<MealEntity> {
        return mealRepository.getRandomMeal()
    }

}