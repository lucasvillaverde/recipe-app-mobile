package dev.lucasvillaverde.recipes.presenter.recipe_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.lucasvillaverde.common.base.domain.None
import dev.lucasvillaverde.common.base.model.BasePageState
import dev.lucasvillaverde.common.base.model.BaseResource
import dev.lucasvillaverde.recipes.domain.usecases.DeleteRecipesUseCase
import dev.lucasvillaverde.recipes.domain.usecases.FetchNewRecipeUseCase
import dev.lucasvillaverde.recipes.domain.usecases.GetRecipeListUseCase
import dev.lucasvillaverde.recipes.presenter.recipe_list.adapter.RecipeListItem
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val fetchNewRecipeUseCase: FetchNewRecipeUseCase,
    private val deleteRecipesUseCase: DeleteRecipesUseCase,
    private val getRecipeListUseCase: GetRecipeListUseCase
) : ViewModel() {
    private val _pageState: MutableLiveData<BasePageState<List<RecipeListItem.Recipe>>> =
        MutableLiveData()
    val pageState: LiveData<BasePageState<List<RecipeListItem.Recipe>>> = _pageState

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
                    data = action.updatedRecipeList
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

    fun getNewRecipes(count: Int) {
        viewModelScope.launch {
            onReduceState(Action.LoadingData)
            when (val newRecipeResource =
                fetchNewRecipeUseCase.execute(FetchNewRecipeUseCase.Params(count))) {
                is BaseResource.Success -> {
                    onReduceState(Action.GetNewRecipeSuccess(newRecipeResource.data!!.map {
                        RecipeListItem.Recipe(it)
                    }))
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
                    onReduceState(Action.DeleteAllRecipesSuccess)
                }
                is BaseResource.Error -> onReduceState(
                    Action.DeleteAllRecipesFailure(newRecipeResource.errorMessage)
                )
            }
        }
    }

    fun fetchRecipeList() {
        viewModelScope.launch {
            onReduceState(Action.LoadingData)
            when (val recipeListResource = getRecipeListUseCase.execute(None)) {
                is BaseResource.Success -> {
                    if (recipeListResource.data!!.size < MINIMUM_RECIPE_ITEMS) {
                        getNewRecipes(MINIMUM_RECIPE_ITEMS - recipeListResource.data!!.size)

                        return@launch
                    }

                    onReduceState(
                        Action.LoadRecipeListSuccess(recipeListResource.data!!.map {
                            RecipeListItem.Recipe(it)
                        })
                    )
                }
                is BaseResource.Error -> onReduceState(
                    Action.LoadRecipeListFailure(recipeListResource.errorMessage)
                )
            }
        }
    }

    internal sealed class Action {
        class LoadRecipeListSuccess(val recipeList: List<RecipeListItem.Recipe>) : Action()
        class LoadRecipeListFailure(val errorMessage: String) : Action()
        class GetNewRecipeSuccess(val updatedRecipeList: List<RecipeListItem.Recipe>) : Action()
        object DeleteAllRecipesSuccess : Action()
        class DeleteAllRecipesFailure(val errorMessage: String) : Action()
        object LoadingData : Action()
    }

    companion object {
        private const val MINIMUM_RECIPE_ITEMS = 8
    }
}