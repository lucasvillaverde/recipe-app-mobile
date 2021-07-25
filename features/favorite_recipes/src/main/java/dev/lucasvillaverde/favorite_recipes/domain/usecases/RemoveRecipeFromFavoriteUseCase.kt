package dev.lucasvillaverde.favorite_recipes.domain.usecases

import dev.lucasvillaverde.common.base.domain.BaseUseCase
import dev.lucasvillaverde.common.base.model.BaseResource
import dev.lucasvillaverde.common.utils.AppConstants.MESSAGES.COMMON_ERROR_MESSAGE
import dev.lucasvillaverde.favorite_recipes.domain.repositories.FavoriteRecipesRepository

class RemoveRecipeFromFavoriteUseCase(private val favoriteRecipesRepository: FavoriteRecipesRepository) :
    BaseUseCase<Nothing, RemoveRecipeFromFavoriteUseCase.Params>() {
    override suspend fun execute(params: Params): BaseResource<Nothing> = try {
        favoriteRecipesRepository.removeRecipeFromFavorite(params.id)

        BaseResource.Success()
    } catch (ex: Exception) {
        BaseResource.Error(ex.message ?: COMMON_ERROR_MESSAGE)
    }

    data class Params(val id: Int)
}