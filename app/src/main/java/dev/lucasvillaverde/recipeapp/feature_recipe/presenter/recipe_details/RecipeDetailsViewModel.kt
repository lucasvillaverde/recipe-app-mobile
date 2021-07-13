package dev.lucasvillaverde.recipeapp.feature_recipe.presenter.recipe_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.lucasvillaverde.recipeapp.feature_recipe.data.local.model.RecipeEntity
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.repositories.RecipeRepository
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
) : ViewModel() {
    fun getRecipe(id: Int): LiveData<RecipeEntity> = recipeRepository.getRecipeById(id).asLiveData()
}