package dev.lucasvillaverde.favorite_recipes.domain.usecases

import dev.lucasvillaverde.common.base.domain.BaseFlowableUseCase
import dev.lucasvillaverde.common.base.domain.None
import dev.lucasvillaverde.common.base.model.BaseResource
import dev.lucasvillaverde.common.core.local.model.RecipeEntity
import dev.lucasvillaverde.favorite_recipes.domain.FavoriteRecipeMapper
import dev.lucasvillaverde.favorite_recipes.domain.model.FavoriteRecipe
import dev.lucasvillaverde.favorite_recipes.domain.repositories.FavoriteRecipesRepository
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