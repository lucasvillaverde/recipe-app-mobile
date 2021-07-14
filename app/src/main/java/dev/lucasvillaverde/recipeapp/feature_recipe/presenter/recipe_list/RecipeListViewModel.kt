package dev.lucasvillaverde.recipeapp.feature_recipe.presenter.recipe_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.lucasvillaverde.recipeapp.base.data.model.BaseResource
import dev.lucasvillaverde.recipeapp.base.presenter.model.BasePageState
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.model.RecipeModel
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.usecases.RecipeListUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val recipeListUseCase: RecipeListUseCase
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
                    data = listOf()
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
                    data = listOf()
                )
                is Action.GetNewRecipeSuccess -> BasePageState(
                    isLoading = false,
                    isError = false,
                    data = listOf()
                )
                is Action.DeleteAllRecipeSuccess -> BasePageState(
                    isLoading = false,
                    isError = false,
                    data = listOf()
                )
            }
        )
    }

    fun getNewRecipe() {
        viewModelScope.launch {
            onReduceState(Action.LoadingData)
            when (val newRecipeResource = recipeListUseCase.fetchNewRecipe()) {
                is BaseResource.Success -> onReduceState(
                    Action.GetNewRecipeSuccess
                )
                is BaseResource.Error -> onReduceState(
                    Action.LoadRecipeListFailure(newRecipeResource.errorMessage)
                )
            }
            fetchRecipeList()
        }
    }

    fun deleteMeals() {
        viewModelScope.launch {
            recipeListUseCase.deleteMeals()
            onReduceState(Action.DeleteAllRecipeSuccess)
            fetchRecipeList()
        }
    }

    private fun fetchRecipeList() {
        viewModelScope.launch {
            onReduceState(Action.LoadingData)
            when (val recipeListResource = recipeListUseCase.getRecipeList()) {
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
        object DeleteAllRecipeSuccess : Action()
        object LoadingData : Action()
    }
}