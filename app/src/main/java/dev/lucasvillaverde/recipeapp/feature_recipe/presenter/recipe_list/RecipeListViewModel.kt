package dev.lucasvillaverde.recipeapp.feature_recipe.presenter.recipe_list

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.repositories.RecipeRepository
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
) : ViewModel() {
    val isLoading = MutableLiveData(false)
    val networkError = MutableLiveData(false)
    private val meals = recipeRepository.getMeals()

    fun getMeals() = meals

    fun getNewMeal() {
        viewModelScope.launch {
            try {
                isLoading.value = true
                recipeRepository.refreshMeals()
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
            recipeRepository.deleteMeals()
            isLoading.value = false
        }
    }
}