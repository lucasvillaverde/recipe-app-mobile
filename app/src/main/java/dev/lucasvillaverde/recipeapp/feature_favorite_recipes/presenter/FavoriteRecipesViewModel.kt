package dev.lucasvillaverde.recipeapp.feature_favorite_recipes.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.lucasvillaverde.recipeapp.base.data.model.BaseResource
import dev.lucasvillaverde.recipeapp.base.domain.None
import dev.lucasvillaverde.recipeapp.feature_favorite_recipes.domain.model.FavoriteRecipe
import dev.lucasvillaverde.recipeapp.feature_favorite_recipes.domain.usecases.GetFavoriteRecipesUseCase
import dev.lucasvillaverde.recipeapp.feature_favorite_recipes.domain.usecases.RemoveRecipeFromFavoriteUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteRecipesViewModel @Inject constructor(
    private val removeRecipeFromFavoriteUseCase: RemoveRecipeFromFavoriteUseCase,
    private val getFavoriteRecipesUseCase: GetFavoriteRecipesUseCase
) : ViewModel() {
    private val _recipes = MutableLiveData<List<FavoriteRecipe>>()
    val recipes: LiveData<List<FavoriteRecipe>> = _recipes

    init {
        fetchFavoriteRecipes()
    }

    fun removeFromFavorite(id: Int) {
        viewModelScope.launch {
            when (removeRecipeFromFavoriteUseCase.execute(RemoveRecipeFromFavoriteUseCase.Params(id))) {
                is BaseResource.Success -> fetchFavoriteRecipes()
                is BaseResource.Error -> _recipes.postValue(listOf())
            }
        }
    }

    fun fetchFavoriteRecipes() {
        viewModelScope.launch {
            when (val favoriteRecipeResource = getFavoriteRecipesUseCase.execute(None)) {
                is BaseResource.Success -> _recipes.postValue(favoriteRecipeResource.data!!)
                is BaseResource.Error -> _recipes.postValue(listOf())
            }
        }
    }
}