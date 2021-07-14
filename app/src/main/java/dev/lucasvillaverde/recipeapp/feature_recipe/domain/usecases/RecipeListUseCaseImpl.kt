package dev.lucasvillaverde.recipeapp.feature_recipe.domain.usecases

import dev.lucasvillaverde.recipeapp.base.data.model.BaseResource
import dev.lucasvillaverde.recipeapp.feature_recipe.data.local.model.toModel
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.model.RecipeModel
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.repositories.RecipeRepository

class RecipeListUseCaseImpl(val recipeRepository: RecipeRepository) : RecipeListUseCase {
    override suspend fun getRecipeList(): BaseResource<List<RecipeModel>> =
        try {
            val recipes = recipeRepository.getRecipes().map { it.toModel() }
            BaseResource.Success(recipes)
        } catch (ex: Exception) {
            BaseResource.Error(ex.message ?: "Oops, something wrong happened.")
        }


    override suspend fun fetchNewRecipe(): BaseResource<Nothing> =
        try {
            recipeRepository.fetchNewRecipe()
            BaseResource.Success(null)
        } catch (ex: Exception) {
            BaseResource.Error(ex.message ?: "Oops, something wrong happened.")
        }

    override suspend fun deleteMeals() = recipeRepository.deleteRecipes()
}