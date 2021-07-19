package dev.lucasvillaverde.recipeapp.feature_favorite_recipes.domain.usecases

import dev.lucasvillaverde.recipeapp.base.data.model.BaseResource
import dev.lucasvillaverde.recipeapp.base.domain.BaseUseCase
import dev.lucasvillaverde.recipeapp.base.domain.None
import dev.lucasvillaverde.recipeapp.feature_favorite_recipes.domain.FavoriteRecipeMapper
import dev.lucasvillaverde.recipeapp.feature_favorite_recipes.domain.model.FavoriteRecipe
import dev.lucasvillaverde.recipeapp.feature_favorite_recipes.domain.repositories.FavoriteRecipesRepository

class GetFavoriteRecipesUseCase(private val favoriteRecipesRepository: FavoriteRecipesRepository) :
    BaseUseCase<List<FavoriteRecipe>, None>() {
    override suspend fun execute(params: None): BaseResource<List<FavoriteRecipe>> = try {
        val favoriteRecipesModel = favoriteRecipesRepository.getFavoriteRecipes()
            .map { FavoriteRecipeMapper.mapFromEntity(it) }

        BaseResource.Success(favoriteRecipesModel)
    } catch (ex: Exception) {
        BaseResource.Error(ex.message ?: "Oops, something wrong happened.")
    }
}