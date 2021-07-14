package dev.lucasvillaverde.recipeapp.feature_recipe.domain.usecases

import dev.lucasvillaverde.recipeapp.base.data.model.BaseResource
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.model.RecipeModel

interface RecipeListUseCase {
    suspend fun getRecipeList(): BaseResource<List<RecipeModel>>
    suspend fun fetchNewRecipe(): BaseResource<Nothing>
    suspend fun deleteMeals()
}