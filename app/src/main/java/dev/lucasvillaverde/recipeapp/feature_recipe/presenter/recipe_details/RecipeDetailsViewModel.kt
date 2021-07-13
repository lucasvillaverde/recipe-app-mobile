package dev.lucasvillaverde.recipeapp.feature_recipe.presenter.recipe_details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.lucasvillaverde.recipeapp.feature_recipe.data.local.model.toModel
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.repositories.RecipeRepository
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.model.RecipeModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
) : ViewModel() {
    private val _recipe: MutableLiveData<RecipeModel> = MutableLiveData()
    val recipe = _recipe

    fun fetchRecipe(id: Int) {
        viewModelScope.launch {
            val recipe = recipeRepository.getRecipeById(id)
            _recipe.postValue(recipe.toModel())
        }
    }
}