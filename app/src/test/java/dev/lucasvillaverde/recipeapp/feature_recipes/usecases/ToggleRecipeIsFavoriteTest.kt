package dev.lucasvillaverde.recipeapp.feature_recipes.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dev.lucasvillaverde.recipeapp.base.data.model.BaseResource
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.repositories.RecipeRepository
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.usecases.ToggleRecipeIsFavorite
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class ToggleRecipeIsFavoriteTest {
    private val fakeRecipeRepository = mockk<RecipeRepository>()
    private val markRecipeAsFavoriteUseCase = ToggleRecipeIsFavorite(fakeRecipeRepository)

    companion object {
        const val DEFAULT_RECIPE_ID = 1
    }

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun itShouldMarkRecipeAsFavoriteSuccessfully() {
        coEvery { fakeRecipeRepository.toggleRecipeIsFavorite(DEFAULT_RECIPE_ID) } returns Unit

        runBlocking {
            val markRecipeAsFavoriteResource = markRecipeAsFavoriteUseCase.execute(
                ToggleRecipeIsFavorite.Params(DEFAULT_RECIPE_ID)
            )
            assertTrue(markRecipeAsFavoriteResource is BaseResource.Success)
        }
    }

    @Test
    fun itShouldMarkRecipeAsFavoriteFailure() {
        coEvery {
            fakeRecipeRepository.toggleRecipeIsFavorite(DEFAULT_RECIPE_ID)
        } throws Exception("Could not mark recipe as favorite")

        runBlocking {
            val markRecipeAsFavoriteResource = markRecipeAsFavoriteUseCase.execute(
                ToggleRecipeIsFavorite.Params(DEFAULT_RECIPE_ID)
            )
            assertTrue(markRecipeAsFavoriteResource is BaseResource.Error)
            assertEquals(
                (markRecipeAsFavoriteResource as BaseResource.Error).errorMessage,
                "Could not mark recipe as favorite"
            )
        }
    }
}