package dev.lucasvillaverde.recipeapp.feature_recipe.presenter.recipe_details

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.repositories.RecipeRepository
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
) : ViewModel() {
    fun getMeal(id: Int) = recipeRepository.getMealById(id)
}