package dev.lucasvillaverde.recipes.domain.usecases

import dev.lucasvillaverde.common.base.domain.BaseUseCase
import dev.lucasvillaverde.common.base.model.BaseResource
import dev.lucasvillaverde.common.utils.AppConstants.MESSAGES.COMMON_ERROR_MESSAGE
import dev.lucasvillaverde.recipes.domain.RecipeMapper
import dev.lucasvillaverde.recipes.domain.model.RecipeModel
import dev.lucasvillaverde.recipes.domain.repositories.RecipeRepository

class GetRecipeUseCase(private val recipeRepository: RecipeRepository) :
    BaseUseCase<RecipeModel, GetRecipeUseCase.Params>() {
    override suspend fun execute(params: Params): BaseResource<RecipeModel> =
        try {
            val recipeEntity = recipeRepository.getRecipeById(params.id)
            val recipeModel = RecipeMapper.mapFromEntity(recipeEntity)
            BaseResource.Success(recipeModel)
        } catch (ex: Exception) {
            BaseResource.Error(ex.message ?: COMMON_ERROR_MESSAGE)
        }

    data class Params(val id: Int)
}