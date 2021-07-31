package dev.lucasvillaverde.favorite_recipes.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.lucasvillaverde.common.base.domain.None
import dev.lucasvillaverde.common.base.model.BaseResource
import dev.lucasvillaverde.favorite_recipes.domain.usecases.GetFavoriteRecipesUseCase
import dev.lucasvillaverde.favorite_recipes.domain.usecases.RemoveRecipeFromFavoriteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteRecipesViewModel @Inject constructor(
    private val removeRecipeFromFavoriteUseCase: RemoveRecipeFromFavoriteUseCase,
    getFavoriteRecipesUseCase: GetFavoriteRecipesUseCase
) : ViewModel() {
    val favoriteRecipes = getFavoriteRecipesUseCase.execute(None).asLiveData()

    fun removeFromFavorite(id: Int) {
        viewModelScope.launch {
            when (removeRecipeFromFavoriteUseCase.execute(
                RemoveRecipeFromFavoriteUseCase.Params(
                    id
                )
            )) {
                is BaseResource.Success -> true
                is BaseResource.Error -> false
            }
        }
    }
}