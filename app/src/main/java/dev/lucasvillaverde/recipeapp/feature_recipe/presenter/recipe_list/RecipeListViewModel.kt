package dev.lucasvillaverde.recipeapp.feature_recipe.presenter.recipe_list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.lucasvillaverde.recipeapp.base.presenter.model.BasePageState
import dev.lucasvillaverde.recipeapp.feature_recipe.data.local.model.toModel

import dev.lucasvillaverde.recipeapp.feature_recipe.domain.repositories.RecipeRepository
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.model.RecipeModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
) : ViewModel() {
    private var recipes = listOf<RecipeModel>()
    private val _pageState: MutableLiveData<BasePageState<List<RecipeModel>>> = MutableLiveData(
        BasePageState()
    )
    val pageState: LiveData<BasePageState<List<RecipeModel>>> = _pageState

    init {
        viewModelScope.launch {
            recipes = getRecipesFromRepository()
            _pageState.postValue(
                _pageState.value?.copy(
                    isLoading = false,
                    isError = false,
                    data = recipes
                )
            )
        }
    }

    fun getNewMeal() {
        val state = BasePageState<List<RecipeModel>>()
        _pageState.value = state
        viewModelScope.launch {
            runCatching {
                recipeRepository.fetchNewRecipe()
                recipes = recipeRepository.getRecipes().map { it.toModel() }
            }.onSuccess {
                _pageState.postValue(
                    state.copy(
                        isLoading = false,
                        isError = false,
                        data = recipes
                    )
                )
            }.onFailure {
                _pageState.postValue(
                    state.copy(
                        isLoading = false,
                        isError = true,
                        data = listOf()
                    )
                )
                Log.d("GETNEWMEAL", "ERROR!")
            }
        }
    }

    fun deleteMeals() {
        val state = BasePageState<List<RecipeModel>>(
            isLoading = true
        )
        _pageState.value = state
        viewModelScope.launch {
            recipeRepository.deleteRecipes()
            _pageState.postValue(
                state.copy(
                    isLoading = false,
                    isError = false,
                    data = listOf()
                )
            )
        }
    }

    private suspend fun getRecipesFromRepository() =
        recipeRepository.getRecipes().map { it.toModel() }
}