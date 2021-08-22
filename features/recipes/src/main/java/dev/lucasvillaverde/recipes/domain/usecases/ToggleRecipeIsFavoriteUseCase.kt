package dev.lucasvillaverde.recipes.domain.usecases

import dev.lucasvillaverde.common.base.domain.BaseUseCase
import dev.lucasvillaverde.common.base.model.BaseResource
import dev.lucasvillaverde.common.utils.AppConstants.MESSAGES.COMMON_ERROR_MESSAGE
import dev.lucasvillaverde.recipes.domain.repositories.RecipeRepository

class ToggleRecipeIsFavoriteUseCase(private val recipeRepository: RecipeRepository) :
    BaseUseCase<Nothing, ToggleRecipeIsFavoriteUseCase.Params>() {
    override suspend fun execute(params: Params): BaseResource<Nothing> = try {
        recipeRepository.toggleRecipeIsFavorite(params.id)

        BaseResource.Success()
    } catch (ex: Exception) {
        BaseResource.Error(ex.message ?: COMMON_ERROR_MESSAGE)
    }

    data class Params(val id: Int)
}