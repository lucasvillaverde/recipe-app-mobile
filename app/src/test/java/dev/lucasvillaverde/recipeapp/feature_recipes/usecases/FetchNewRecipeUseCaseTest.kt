package dev.lucasvillaverde.recipeapp.feature_recipes.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dev.lucasvillaverde.recipeapp.base.data.model.BaseResource
import dev.lucasvillaverde.recipeapp.base.domain.None
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.repositories.RecipeRepository
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.usecases.FetchNewRecipeUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class FetchNewRecipeUseCaseTest {
    private val fakeRecipeRepository = mockk<RecipeRepository>()
    private val fetchNewRecipeUseCase = FetchNewRecipeUseCase(fakeRecipeRepository)

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun itShouldFetchNewRecipeSuccessfully() {
        coEvery { fakeRecipeRepository.fetchNewRecipe() } returns Unit

        runBlocking {
            val fetchNewRecipeResource = fetchNewRecipeUseCase.execute(None)
            assertTrue(fetchNewRecipeResource is BaseResource.Success)
        }
    }

    @Test
    fun itShouldFetchNewRecipeFailure() {
        coEvery { fakeRecipeRepository.fetchNewRecipe() } throws Exception("Network error while fetching")

        runBlocking {
            val fetchNewRecipeResource = fetchNewRecipeUseCase.execute(None)
            assertTrue(fetchNewRecipeResource is BaseResource.Error)
            assertEquals(
                (fetchNewRecipeResource as BaseResource.Error).errorMessage,
                "Network error while fetching"
            )
        }
    }
}