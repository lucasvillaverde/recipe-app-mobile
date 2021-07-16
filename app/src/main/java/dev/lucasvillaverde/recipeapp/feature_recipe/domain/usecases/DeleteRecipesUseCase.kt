package dev.lucasvillaverde.recipeapp.feature_recipe.domain.usecases

import dev.lucasvillaverde.recipeapp.base.data.model.BaseResource
import dev.lucasvillaverde.recipeapp.base.domain.BaseUseCase
import dev.lucasvillaverde.recipeapp.base.domain.None
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.repositories.RecipeRepository

class DeleteRecipesUseCase(private val recipeRepository: RecipeRepository) :
    BaseUseCase<Nothing, None>() {
    override suspend fun execute(params: None): BaseResource<Nothing> =
        try {
            recipeRepository.deleteRecipes()
            BaseResource.Success()
        } catch (ex: Exception) {
            BaseResource.Error(ex.message ?: "Oops, something wrong happened.")
        }
}