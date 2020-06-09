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

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val mealRepository = MealRepository(getDatabase(application))
    private val meals = mealRepository.meals

    fun getMeals() = meals

}