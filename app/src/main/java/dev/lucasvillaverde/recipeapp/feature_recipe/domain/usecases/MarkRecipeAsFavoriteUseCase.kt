package dev.lucasvillaverde.recipeapp.feature_recipe.domain.usecases

import dev.lucasvillaverde.recipeapp.base.data.model.BaseResource
import dev.lucasvillaverde.recipeapp.base.domain.BaseUseCase
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.repositories.RecipeRepository
import dev.lucasvillaverde.recipeapp.utils.AppConstants.MESSAGES.COMMON_ERROR_MESSAGE

class MarkRecipeAsFavoriteUseCase(private val recipeRepository: RecipeRepository) :
    BaseUseCase<Nothing, MarkRecipeAsFavoriteUseCase.Params>() {
    override suspend fun execute(params: Params): BaseResource<Nothing> = try {
        recipeRepository.markRecipeAsFavorite(params.id)
        BaseResource.Success()
    } catch (ex: Exception) {
        BaseResource.Error(ex.message ?: COMMON_ERROR_MESSAGE)
    }

    data class Params(val id: Int)
}