package dev.lucasvillaverde.recipeapp.feature_recipe.domain.usecases

import dev.lucasvillaverde.recipeapp.base.data.model.BaseResource
import dev.lucasvillaverde.recipeapp.base.domain.BaseUseCase
import dev.lucasvillaverde.recipeapp.base.domain.None
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.repositories.RecipeRepository
import dev.lucasvillaverde.recipeapp.utils.AppConstants.MESSAGES.COMMON_ERROR_MESSAGE

class FetchNewRecipeUseCase(private val recipeRepository: RecipeRepository) :
    BaseUseCase<Nothing, None>() {
    override suspend fun execute(params: None): BaseResource<Nothing> =
        try {
            recipeRepository.fetchNewRecipe()
            BaseResource.Success()
        } catch (ex: Exception) {
            BaseResource.Error(ex.message ?: COMMON_ERROR_MESSAGE)
        }
}