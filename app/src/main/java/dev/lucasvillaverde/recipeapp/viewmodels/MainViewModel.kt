package dev.lucasvillaverde.recipeapp.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.lucasvillaverde.recipeapp.data.repositories.MealRepository
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mealRepository: MealRepository
) : ViewModel() {

    val isLoading = MutableLiveData(false)
    val networkError = MutableLiveData(false)
    private val meals = mealRepository.meals

    fun getMeals() = meals

    fun getNewMeal() {
        viewModelScope.launch {
            try {
                isLoading.value = true
                mealRepository.refreshMeals()
                isLoading.value = false
            } catch (ex: IOException) {
                networkError.value = true
                Log.d("GETNEWMEAL", "ERROR!")
            }
        }
    }

    fun deleteMeals() {
        viewModelScope.launch {
            isLoading.value = true
            mealRepository.deleteMeals()
            isLoading.value = false
        }
    }
}