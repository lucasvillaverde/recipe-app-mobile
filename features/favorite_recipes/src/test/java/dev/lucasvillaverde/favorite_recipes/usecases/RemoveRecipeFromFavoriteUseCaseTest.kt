package dev.lucasvillaverde.favorite_recipes.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dev.lucasvillaverde.common.base.model.BaseResource
import dev.lucasvillaverde.favorite_recipes.domain.repositories.FavoriteRecipesRepository
import dev.lucasvillaverde.favorite_recipes.domain.usecases.RemoveRecipeFromFavoriteUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class RemoveRecipeFromFavoriteUseCaseTest {
    private val fakeFavoriteRecipesRepository = mockk<FavoriteRecipesRepository>()
    private val removeRecipeFromFavoriteUseCase =
        RemoveRecipeFromFavoriteUseCase(
            fakeFavoriteRecipesRepository
        )

    companion object {
        private const val DEFAULT_RECIPE_ID = 1
    }

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun itShouldRemoveRecipeFromFavoriteSuccessfully() {
        coEvery { fakeFavoriteRecipesRepository.removeRecipeFromFavorite(DEFAULT_RECIPE_ID) } returns Unit

        runBlocking {
            val removedRecipeFromFavoritesResource = removeRecipeFromFavoriteUseCase.execute(
                RemoveRecipeFromFavoriteUseCase.Params(
                    DEFAULT_RECIPE_ID
                )
            )
            assertTrue(removedRecipeFromFavoritesResource is BaseResource.Success)
        }
    }

    @Test
    fun itShouldRemoveRecipeFromFavoriteFailure() {
        coEvery { fakeFavoriteRecipesRepository.removeRecipeFromFavorite(DEFAULT_RECIPE_ID) } throws Exception(
            "Could not find recipe id"
        )

        runBlocking {
            val removedRecipeFromFavoritesResource = removeRecipeFromFavoriteUseCase.execute(
                RemoveRecipeFromFavoriteUseCase.Params(
                    DEFAULT_RECIPE_ID
                )
            )
            assertTrue(removedRecipeFromFavoritesResource is BaseResource.Error)
            assertEquals(
                (removedRecipeFromFavoritesResource as BaseResource.Error).errorMessage,
                "Could not find recipe id"
            )
        }
    }
}