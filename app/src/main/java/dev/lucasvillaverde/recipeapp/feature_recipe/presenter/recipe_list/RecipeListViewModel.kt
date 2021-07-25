package dev.lucasvillaverde.recipeapp.feature_recipe.presenter.recipe_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.lucasvillaverde.common.base.model.BaseResource
import dev.lucasvillaverde.common.base.domain.None
import dev.lucasvillaverde.common.base.model.BasePageState
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.model.RecipeModel
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.usecases.DeleteRecipesUseCase
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.usecases.FetchNewRecipeUseCase
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.usecases.GetRecipeListUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val fetchNewRecipeUseCase: FetchNewRecipeUseCase,
    private val deleteRecipesUseCase: DeleteRecipesUseCase,
    private val getRecipeListUseCase: GetRecipeListUseCase
) : ViewModel() {
    private val _pageState: MutableLiveData<BasePageState<List<RecipeModel>>> = MutableLiveData()
    val pageState: LiveData<BasePageState<List<RecipeModel>>> = _pageState

    init {
        fetchRecipeList()
    }

    private fun onReduceState(action: Action) {
        _pageState.postValue(
            when (action) {
                is Action.LoadingData -> BasePageState(
                    isLoading = true,
                    isError = false,
                    data = null
                )
                is Action.LoadRecipeListSuccess -> BasePageState(
                    isLoading = false,
                    isError = false,
                    data = action.recipeList
                )
                is Action.LoadRecipeListFailure -> BasePageState(
                    isLoading = false,
                    isError = true,
                    errorMessage = action.errorMessage,
                    data = null
                )
                is Action.GetNewRecipeSuccess -> BasePageState(
                    isLoading = false,
                    isError = false,
                    data = null
                )
                is Action.DeleteAllRecipesSuccess -> BasePageState(
                    isLoading = false,
                    isError = false,
                    data = listOf()
                )
                is Action.DeleteAllRecipesFailure -> BasePageState(
                    isLoading = false,
                    isError = false,
                    errorMessage = action.errorMessage,
                    data = null
                )
            }
        )
    }

    fun getNewRecipe() {
        viewModelScope.launch {
            onReduceState(Action.LoadingData)
            when (val newRecipeResource = fetchNewRecipeUseCase.execute(None)) {
                is BaseResource.Success -> {
                    onReduceState(Action.GetNewRecipeSuccess)
                    fetchRecipeList()
                }
                is BaseResource.Error -> onReduceState(
                    Action.LoadRecipeListFailure(newRecipeResource.errorMessage)
                )
            }
        }
    }

    fun deleteMeals() {
        viewModelScope.launch {
            onReduceState(Action.LoadingData)
            when (val newRecipeResource = deleteRecipesUseCase.execute(None)) {
                is BaseResource.Success -> {
                    onReduceState(Action.GetNewRecipeSuccess)
                    fetchRecipeList()
                }
                is BaseResource.Error -> onReduceState(
                    Action.DeleteAllRecipesFailure(newRecipeResource.errorMessage)
                )
            }
        }
    }

    private fun fetchRecipeList() {
        viewModelScope.launch {
            onReduceState(Action.LoadingData)
            when (val recipeListResource = getRecipeListUseCase.execute(None)) {
                is BaseResource.Success -> onReduceState(
                    Action.LoadRecipeListSuccess(recipeListResource.data!!)
                )
                is BaseResource.Error -> onReduceState(
                    Action.LoadRecipeListFailure(recipeListResource.errorMessage)
                )
            }
        }
    }

    internal sealed class Action {
        class LoadRecipeListSuccess(val recipeList: List<RecipeModel>) : Action()
        class LoadRecipeListFailure(val errorMessage: String) : Action()
        object GetNewRecipeSuccess : Action()
        object DeleteAllRecipesSuccess : Action()
        class DeleteAllRecipesFailure(val errorMessage: String) : Action()
        object LoadingData : Action()
    }
}