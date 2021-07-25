package dev.lucasvillaverde.recipeapp.feature_favorite_recipes.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dev.lucasvillaverde.recipeapp.base.data.model.BaseResource
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
    private val fakeFavoriteRecipesRepository = mockk<dev.lucasvillaverde.favorite_recipes.domain.repositories.FavoriteRecipesRepository>()
    private val removeRecipeFromFavoriteUseCase =
        dev.lucasvillaverde.favorite_recipes.domain.usecases.RemoveRecipeFromFavoriteUseCase(
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
                dev.lucasvillaverde.favorite_recipes.domain.usecases.RemoveRecipeFromFavoriteUseCase.Params(
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
                dev.lucasvillaverde.favorite_recipes.domain.usecases.RemoveRecipeFromFavoriteUseCase.Params(
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