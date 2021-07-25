package dev.lucasvillaverde.recipeapp.feature_recipe.domain.usecases

import dev.lucasvillaverde.common.base.model.BaseResource
import dev.lucasvillaverde.common.base.domain.BaseUseCase
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.RecipeMapper
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.model.RecipeModel
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.repositories.RecipeRepository
import dev.lucasvillaverde.common.utils.AppConstants.MESSAGES.COMMON_ERROR_MESSAGE

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