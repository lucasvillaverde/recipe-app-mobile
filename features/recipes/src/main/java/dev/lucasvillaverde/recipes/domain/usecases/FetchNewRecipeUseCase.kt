package dev.lucasvillaverde.recipes.domain.usecases

import dev.lucasvillaverde.common.base.domain.BaseUseCase
import dev.lucasvillaverde.common.base.model.BaseResource
import dev.lucasvillaverde.common.utils.AppConstants.MESSAGES.COMMON_ERROR_MESSAGE
import dev.lucasvillaverde.recipes.domain.RecipeMapper
import dev.lucasvillaverde.recipes.domain.model.RecipeModel
import dev.lucasvillaverde.recipes.domain.repositories.RecipeRepository

class FetchNewRecipeUseCase(private val recipeRepository: RecipeRepository) :
    BaseUseCase<List<RecipeModel>, FetchNewRecipeUseCase.Params>() {
    override suspend fun execute(params: Params): BaseResource<List<RecipeModel>> =
        try {
            for (i in 1..params.count) {
                recipeRepository.fetchNewRecipe()
            }

            val latestRecipeList = recipeRepository.getRecipes().map {
                RecipeMapper.mapFromEntity(it)
            }

            BaseResource.Success(latestRecipeList)
        } catch (ex: Exception) {
            BaseResource.Error(ex.message ?: COMMON_ERROR_MESSAGE)
        }

    class Params(val count: Int)
}