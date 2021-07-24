package dev.lucasvillaverde.recipeapp.feature_favorite_recipes.domain.usecases

import dev.lucasvillaverde.recipeapp.base.data.model.BaseResource
import dev.lucasvillaverde.recipeapp.base.domain.BaseFlowableUseCase
import dev.lucasvillaverde.recipeapp.base.domain.None
import dev.lucasvillaverde.recipeapp.core.data.local.model.RecipeEntity
import dev.lucasvillaverde.recipeapp.feature_favorite_recipes.domain.FavoriteRecipeMapper
import dev.lucasvillaverde.recipeapp.feature_favorite_recipes.domain.model.FavoriteRecipe
import dev.lucasvillaverde.recipeapp.feature_favorite_recipes.domain.repositories.FavoriteRecipesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map


class GetFavoriteRecipesUseCase(private val favoriteRecipesRepository: FavoriteRecipesRepository) :
    BaseFlowableUseCase<List<FavoriteRecipe>, None>() {
    override fun execute(params: None): Flow<BaseResource<List<FavoriteRecipe>>> =
        favoriteRecipesRepository
            .getFavoriteRecipes()
            .map<List<RecipeEntity>, BaseResource<List<FavoriteRecipe>>> { recipeEntityList ->
                val data = recipeEntityList.map { FavoriteRecipeMapper.mapFromEntity(it) }
                BaseResource.Success(data)
            }
            .catch { cause ->
                emit(BaseResource.Error(cause.message!!))
            }
}