package dev.lucasvillaverde.recipeapp.feature_recipe.domain.usecases

import dev.lucasvillaverde.recipeapp.base.data.model.BaseResource
import dev.lucasvillaverde.recipeapp.base.domain.BaseUseCase
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.repositories.RecipeRepository
import dev.lucasvillaverde.recipeapp.utils.AppConstants.MESSAGES.COMMON_ERROR_MESSAGE

class ToggleRecipeIsFavorite(private val recipeRepository: RecipeRepository) :
    BaseUseCase<Nothing, ToggleRecipeIsFavorite.Params>() {
    override suspend fun execute(params: Params): BaseResource<Nothing> = try {
        recipeRepository.toggleRecipeIsFavorite(params.id)

        BaseResource.Success()
    } catch (ex: Exception) {
        BaseResource.Error(ex.message ?: COMMON_ERROR_MESSAGE)
    }

    data class Params(val id: Int)
}