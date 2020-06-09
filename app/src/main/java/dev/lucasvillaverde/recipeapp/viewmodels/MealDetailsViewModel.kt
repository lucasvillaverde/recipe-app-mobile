package dev.lucasvillaverde.recipeapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dev.lucasvillaverde.recipeapp.data.local.getDatabase
import dev.lucasvillaverde.recipeapp.data.repositories.MealRepository
import kotlinx.coroutines.launch
import java.io.IOException

class MealDetailsViewModel(application: Application) : AndroidViewModel(application) {

    val mealRepository = MealRepository(getDatabase(application))
    private val meals = mealRepository.meals
    private var _networkError = MutableLiveData(false)

    init {
        refreshDataFromRepository()
    }

    fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                mealRepository.refreshMeals()
                _networkError.value = false
            } catch (error: IOException) {
                if (meals.value.isNullOrEmpty()) {
                    _networkError.value = true
                    Log.d("ERROR", "ERROR: " + error.localizedMessage)
                }
            }
        }
    }

    fun getRandomMeal() = mealRepository.meals.value?.random()

}