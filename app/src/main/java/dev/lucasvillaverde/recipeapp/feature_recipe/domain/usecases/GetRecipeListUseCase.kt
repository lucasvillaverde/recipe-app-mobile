package dev.lucasvillaverde.recipeapp.feature_recipe.domain.usecases

import dev.lucasvillaverde.common.base.model.BaseResource
import dev.lucasvillaverde.common.base.domain.BaseUseCase
import dev.lucasvillaverde.common.base.domain.None
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.RecipeMapper
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.model.RecipeModel
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.repositories.RecipeRepository
import dev.lucasvillaverde.common.utils.AppConstants.MESSAGES.COMMON_ERROR_MESSAGE

class GetRecipeListUseCase(private val recipeRepository: RecipeRepository) :
    BaseUseCase<List<RecipeModel>, None>() {
    override suspend fun execute(params: None): BaseResource<List<RecipeModel>> =
        try {
            val recipes = recipeRepository.getRecipes().map { RecipeMapper.mapFromEntity(it) }
            BaseResource.Success(recipes)
        } catch (ex: Exception) {
            BaseResource.Error(ex.message ?: COMMON_ERROR_MESSAGE)
        }
}