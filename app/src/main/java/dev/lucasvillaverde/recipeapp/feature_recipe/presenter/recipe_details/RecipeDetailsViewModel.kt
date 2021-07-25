package dev.lucasvillaverde.recipeapp.feature_recipe.presenter.recipe_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.lucasvillaverde.common.base.model.BaseResource
import dev.lucasvillaverde.common.base.model.BasePageState
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.model.RecipeModel
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.usecases.GetRecipeUseCase
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.usecases.ToggleRecipeIsFavorite
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val getRecipeUseCase: GetRecipeUseCase,
    private val toggleFavoriteRecipeUseCase: ToggleRecipeIsFavorite
) : ViewModel() {
    private val _pageState = MutableLiveData<BasePageState<RecipeModel>>()
    val pageState: LiveData<BasePageState<RecipeModel>> = _pageState

    private fun onReduceState(action: Action) {
        _pageState.postValue(
            when (action) {
                is Action.LoadingData -> BasePageState(
                    isLoading = true,
                    isError = false,
                    data = null
                )
                is Action.LoadRecipeSuccess -> BasePageState(
                    isLoading = false,
                    isError = false,
                    data = action.recipe
                )
                is Action.LoadRecipeFailure -> BasePageState(
                    isLoading = false,
                    isError = true,
                    errorMessage = action.errorMessage,
                    data = null
                )
            }
        )
    }

    fun getRecipe(id: Int) {
        viewModelScope.launch {
            onReduceState(Action.LoadingData)
            when (val newRecipeResource = getRecipeUseCase.execute(GetRecipeUseCase.Params(id))) {
                is BaseResource.Success -> {
                    onReduceState(Action.LoadRecipeSuccess(newRecipeResource.data!!))
                }
                is BaseResource.Error -> onReduceState(
                    Action.LoadRecipeFailure(newRecipeResource.errorMessage)
                )
            }
        }
    }

    fun toggleRecipeIsFavorite(id: Int) {
        viewModelScope.launch {
            onReduceState(Action.LoadingData)
            when (toggleFavoriteRecipeUseCase.execute(
                ToggleRecipeIsFavorite.Params(id)
            )) {
                is BaseResource.Success -> getRecipe(id)
                is BaseResource.Error -> TODO()
            }
        }
    }

    internal sealed class Action {
        class LoadRecipeSuccess(val recipe: RecipeModel) : Action()
        class LoadRecipeFailure(val errorMessage: String) : Action()
        object LoadingData : Action()
    }
}