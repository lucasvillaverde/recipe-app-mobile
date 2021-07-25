package dev.lucasvillaverde.favorite_recipes.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.lucasvillaverde.recipeapp.base.data.model.BaseResource
import dev.lucasvillaverde.recipeapp.base.domain.None
import dev.lucasvillaverde.favorite_recipes.domain.usecases.GetFavoriteRecipesUseCase
import dev.lucasvillaverde.favorite_recipes.domain.usecases.RemoveRecipeFromFavoriteUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteRecipesViewModel @Inject constructor(
    private val removeRecipeFromFavoriteUseCase: dev.lucasvillaverde.favorite_recipes.domain.usecases.RemoveRecipeFromFavoriteUseCase,
    getFavoriteRecipesUseCase: dev.lucasvillaverde.favorite_recipes.domain.usecases.GetFavoriteRecipesUseCase
) : ViewModel() {
    val favoriteRecipes = getFavoriteRecipesUseCase.execute(None).asLiveData()

    fun removeFromFavorite(id: Int) {
        viewModelScope.launch {
            when (removeRecipeFromFavoriteUseCase.execute(dev.lucasvillaverde.favorite_recipes.domain.usecases.RemoveRecipeFromFavoriteUseCase.Params(id))) {
                is BaseResource.Success -> true
                is BaseResource.Error -> false
            }
        }
    }
}