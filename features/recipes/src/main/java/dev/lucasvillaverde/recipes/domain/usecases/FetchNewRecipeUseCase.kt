package dev.lucasvillaverde.recipes.domain.usecases

import dev.lucasvillaverde.common.base.domain.BaseUseCase
import dev.lucasvillaverde.common.base.model.BaseResource
import dev.lucasvillaverde.common.utils.AppConstants.MESSAGES.COMMON_ERROR_MESSAGE
import dev.lucasvillaverde.recipes.domain.repositories.RecipeRepository

class FetchNewRecipeUseCase(private val recipeRepository: RecipeRepository) :
    BaseUseCase<Nothing, FetchNewRecipeUseCase.Params>() {
    override suspend fun execute(params: Params): BaseResource<Nothing> =
        try {
            for (i in 0..params.count) {
                recipeRepository.fetchNewRecipe()
            }

            BaseResource.Success()
        } catch (ex: Exception) {
            BaseResource.Error(ex.message ?: COMMON_ERROR_MESSAGE)
        }

    class Params(val count: Int)
}