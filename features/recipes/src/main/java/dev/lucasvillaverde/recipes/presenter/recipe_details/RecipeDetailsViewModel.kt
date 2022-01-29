package dev.lucasvillaverde.recipes.presenter.recipe_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.lucasvillaverde.common.base.model.BasePageState
import dev.lucasvillaverde.common.base.model.BaseResource
import dev.lucasvillaverde.recipes.domain.model.RecipeModel
import dev.lucasvillaverde.recipes.domain.usecases.GetRecipeUseCase
import dev.lucasvillaverde.recipes.domain.usecases.ToggleRecipeIsFavoriteUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val getRecipeUseCase: GetRecipeUseCase,
    private val toggleFavoriteRecipeUseCaseUseCase: ToggleRecipeIsFavoriteUseCase
) : ViewModel() {
    private val _pageState = MutableLiveData<BasePageState<RecipeModel>>(
        BasePageState(data = null)
    )
    val pageState: LiveData<BasePageState<RecipeModel>> = _pageState

    private fun onReduceState(action: Action) {
        _pageState.postValue(
            when (action) {
                is Action.LoadingData -> _pageState.value?.copy(
                    isLoading = true,
                    isError = false,
                    errorMessage = null
                )
                is Action.LoadRecipeSuccess -> _pageState.value?.copy(
                    isLoading = false,
                    isError = false,
                    errorMessage = null,
                    data = action.recipe
                )
                is Action.LoadRecipeFailure -> _pageState.value?.copy(
                    isLoading = true,
                    isError = true,
                    errorMessage = action.errorMessage
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
            when (toggleFavoriteRecipeUseCaseUseCase.execute(
                ToggleRecipeIsFavoriteUseCase.Params(id)
            )) {
                is BaseResource.Success -> _pageState.value?.data?.let { recipe ->
                    onReduceState(Action.LoadRecipeSuccess(recipe.copy(isFavorite = !recipe.isFavorite)))
                }
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