package dev.lucasvillaverde.recipeapp.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.lucasvillaverde.recipeapp.data.repositories.MealRepository
import javax.inject.Inject

@HiltViewModel
class MealDetailsViewModel @Inject constructor(
    private val mealRepository: MealRepository
) : ViewModel() {
    fun getMeal(id: Int) = mealRepository.getMealById(id)
}