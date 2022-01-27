package dev.lucasvillaverde.recipes.domain.usecases

import dev.lucasvillaverde.common.base.domain.BaseUseCase
import dev.lucasvillaverde.common.base.model.BaseResource
import dev.lucasvillaverde.common.utils.AppConstants.MESSAGES.COMMON_ERROR_MESSAGE
import dev.lucasvillaverde.recipes.domain.RecipeMapper
import dev.lucasvillaverde.recipes.domain.model.RecipeModel
import dev.lucasvillaverde.recipes.domain.repositories.RecipeRepository

class FilterRecipesUseCase(private val recipeRepository: RecipeRepository) :
    BaseUseCase<List<RecipeModel>, FilterRecipesUseCase.Params>() {
    override suspend fun execute(params: Params): BaseResource<List<RecipeModel>> = try {
        val recipes = recipeRepository.getRecipesByName(params.recipeName)
            .map { RecipeMapper.mapFromEntity(it) }

        BaseResource.Success(data = recipes)
    } catch (ex: Exception) {
        BaseResource.Error(ex.message ?: COMMON_ERROR_MESSAGE)
    }

    data class Params(val recipeName: String)
}