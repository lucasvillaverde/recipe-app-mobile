package dev.lucasvillaverde.recipes.presenter.recipe_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.lucasvillaverde.common.base.domain.None
import dev.lucasvillaverde.common.base.model.BasePageState
import dev.lucasvillaverde.common.base.model.BaseResource
import dev.lucasvillaverde.recipes.domain.model.RecipeModel
import dev.lucasvillaverde.recipes.domain.usecases.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val fetchNewRecipeUseCase: FetchNewRecipeUseCase,
    private val deleteRecipesUseCase: DeleteRecipesUseCase,
    private val getRecipeListUseCase: GetRecipeListUseCase,
    private val toggleRecipeIsFavoriteUseCase: ToggleRecipeIsFavoriteUseCase,
    private val filterRecipesUseCase: FilterRecipesUseCase
) : ViewModel() {
    private val _pageState: MutableLiveData<BasePageState<List<RecipeModel>>> =
        MutableLiveData(
            BasePageState(
                isLoading = false,
                isError = false,
                data = listOf(),
                errorMessage = null
            )
        )
    val pageState: LiveData<BasePageState<List<RecipeModel>>> = _pageState

    init {
        fetchRecipeList()
    }

    private fun onReduceState(action: Action) {
        _pageState.postValue(
            when (action) {
                is Action.LoadingData -> _pageState.value?.copy(
                    isLoading = true,
                    isError = false,
                    errorMessage = null
                )
                is Action.LoadRecipeListSuccess -> _pageState.value?.copy(
                    isLoading = false,
                    isError = false,
                    data = action.recipeList,
                    errorMessage = null
                )
                is Action.LoadRecipeListFailure -> _pageState.value?.copy(
                    isLoading = false,
                    isError = true,
                    errorMessage = action.errorMessage
                )
                is Action.FavoriteRecipeSuccess -> _pageState.value?.copy(
                    isLoading = false,
                    isError = false,
                    data = action.recipeList
                )
                is Action.FavoriteRecipeFailure -> _pageState.value?.copy(
                    isLoading = false,
                    isError = true,
                    errorMessage = action.errorMessage
                )
                is Action.GetNewRecipeSuccess -> _pageState.value?.copy(
                    isLoading = false,
                    isError = false,
                    data = action.updatedRecipeList,
                    errorMessage = null
                )
                is Action.FilterRecipeListSuccess -> _pageState.value?.copy(
                    isLoading = false,
                    isError = false,
                    data = action.updatedRecipeList,
                    errorMessage = null
                )
                is Action.FilterRecipeListFailure -> _pageState.value?.copy(
                    isLoading = false,
                    isError = true,
                    errorMessage = action.errorMessage
                )
                is Action.DeleteAllRecipesSuccess -> _pageState.value?.copy(
                    isLoading = false,
                    isError = false,
                    data = listOf(),
                    errorMessage = null
                )
                is Action.DeleteAllRecipesFailure -> _pageState.value?.copy(
                    isLoading = false,
                    isError = true,
                    errorMessage = action.errorMessage
                )
            }
        )
    }

    fun favoriteRecipe(recipeId: Int) {
        viewModelScope.launch {
            when (
                val favoriteRecipeResource =
                    toggleRecipeIsFavoriteUseCase.execute(
                        ToggleRecipeIsFavoriteUseCase.Params(
                            recipeId
                        )
                    )
            ) {
                is BaseResource.Success -> {
                    val recipeList = _pageState.value?.data?.map { recipe ->
                        if (recipe.id == recipeId)
                            recipe.copy(isFavorite = !recipe.isFavorite)
                        else
                            recipe
                    } ?: listOf()
                    onReduceState(Action.FavoriteRecipeSuccess(recipeList))
                }
                is BaseResource.Error -> onReduceState(
                    Action.FavoriteRecipeFailure(favoriteRecipeResource.errorMessage)
                )
            }
        }
    }

    fun getNewRecipes(count: Int) {
        viewModelScope.launch {
            onReduceState(Action.LoadingData)
            when (val newRecipeResource =
                fetchNewRecipeUseCase.execute(FetchNewRecipeUseCase.Params(count))) {
                is BaseResource.Success -> {
                    onReduceState(Action.GetNewRecipeSuccess(newRecipeResource.data!!.map {
                        it
                    }))
                }
                is BaseResource.Error -> onReduceState(
                    Action.LoadRecipeListFailure(newRecipeResource.errorMessage)
                )
            }
        }
    }

    fun deleteRecipes() {
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
        onReduceState(Action.LoadingData)
        viewModelScope.launch {
            when (val recipeListResource = getRecipeListUseCase.execute(None)) {
                is BaseResource.Success -> {
                    if (recipeListResource.data!!.size < MINIMUM_RECIPE_ITEMS) {
                        getNewRecipes(MINIMUM_RECIPE_ITEMS - recipeListResource.data!!.size)

                        return@launch
                    }

                    onReduceState(
                        Action.LoadRecipeListSuccess(recipeListResource.data!!.map {
                            it
                        })
                    )
                }
                is BaseResource.Error -> onReduceState(
                    Action.LoadRecipeListFailure(recipeListResource.errorMessage)
                )
            }
        }
    }

    fun filterRecipeList(recipeName: String) {
        onReduceState(Action.LoadingData)
        viewModelScope.launch {
            if (recipeName.isBlank()) {
                fetchRecipeList()

                return@launch
            }

            when (val filteredRecipes =
                filterRecipesUseCase.execute(FilterRecipesUseCase.Params(recipeName))) {
                is BaseResource.Success ->
                    onReduceState(
                        Action.FilterRecipeListSuccess(filteredRecipes.data!!.map {
                            it
                        })
                    )
                is BaseResource.Error -> onReduceState(
                    Action.FilterRecipeListFailure(filteredRecipes.errorMessage)
                )
            }
        }
    }

    internal sealed class Action {
        class LoadRecipeListSuccess(val recipeList: List<RecipeModel>) : Action()
        class LoadRecipeListFailure(val errorMessage: String) : Action()
        class FavoriteRecipeSuccess(val recipeList: List<RecipeModel>) : Action()
        class FavoriteRecipeFailure(val errorMessage: String) : Action()
        class GetNewRecipeSuccess(val updatedRecipeList: List<RecipeModel>) : Action()
        class FilterRecipeListSuccess(val updatedRecipeList: List<RecipeModel>) : Action()
        class FilterRecipeListFailure(val errorMessage: String) : Action()
        object DeleteAllRecipesSuccess : Action()
        class DeleteAllRecipesFailure(val errorMessage: String) : Action()
        object LoadingData : Action()
    }

    companion object {
        private const val MINIMUM_RECIPE_ITEMS = 8
    }
}