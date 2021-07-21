package dev.lucasvillaverde.recipeapp.feature_favorite_recipes.domain.usecases

import dev.lucasvillaverde.recipeapp.base.data.model.BaseResource
import dev.lucasvillaverde.recipeapp.base.domain.BaseUseCase
import dev.lucasvillaverde.recipeapp.base.domain.None
import dev.lucasvillaverde.recipeapp.feature_favorite_recipes.domain.FavoriteRecipeMapper
import dev.lucasvillaverde.recipeapp.feature_favorite_recipes.domain.model.FavoriteRecipe
import dev.lucasvillaverde.recipeapp.feature_favorite_recipes.domain.repositories.FavoriteRecipesRepository
import dev.lucasvillaverde.recipeapp.utils.AppConstants.MESSAGES.COMMON_ERROR_MESSAGE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetFavoriteRecipesUseCase(private val favoriteRecipesRepository: FavoriteRecipesRepository) :
    BaseUseCase<List<FavoriteRecipe>, None>() {
    override suspend fun execute(params: None): BaseResource<List<FavoriteRecipe>> = try {
        val favoriteRecipesModel = favoriteRecipesRepository.getFavoriteRecipes()
            .map { FavoriteRecipeMapper.mapFromEntity(it) }

        BaseResource.Success(favoriteRecipesModel)
    } catch (ex: Exception) {
        BaseResource.Error(ex.message ?: COMMON_ERROR_MESSAGE)
    }

    fun executeFlow(): Flow<List<FavoriteRecipe>> {
        return favoriteRecipesRepository
            .getFavoriteRecipesFlow()
            .map { recipeEntityList ->
                recipeEntityList.map { FavoriteRecipeMapper.mapFromEntity(it) }
            }
    }
}