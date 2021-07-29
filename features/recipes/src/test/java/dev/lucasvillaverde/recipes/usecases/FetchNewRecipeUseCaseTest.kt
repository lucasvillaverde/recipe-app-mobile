package dev.lucasvillaverde.recipes.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dev.lucasvillaverde.common.base.model.BaseResource
import dev.lucasvillaverde.recipes.domain.usecases.FetchNewRecipeUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class FetchNewRecipeUseCaseTest {
    private val fakeRecipeRepository =
        mockk<dev.lucasvillaverde.recipes.domain.repositories.RecipeRepository>()
    private val fetchNewRecipeUseCase =
        FetchNewRecipeUseCase(fakeRecipeRepository)

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun itShouldFetchNewRecipeSuccessfully() {
        coEvery { fakeRecipeRepository.fetchNewRecipe() } returns Unit
        coEvery { fakeRecipeRepository.getRecipes() } returns listOf()

        runBlocking {
            val fetchNewRecipeResource =
                fetchNewRecipeUseCase.execute(FetchNewRecipeUseCase.Params(1))
            assertTrue(fetchNewRecipeResource is BaseResource.Success)
        }
    }

    @Test
    fun itShouldFetchNewRecipeFailure() {
        coEvery { fakeRecipeRepository.fetchNewRecipe() } throws Exception("Network error while fetching")

        runBlocking {
            val fetchNewRecipeResource =
                fetchNewRecipeUseCase.execute(FetchNewRecipeUseCase.Params(1))
            assertTrue(fetchNewRecipeResource is BaseResource.Error)
            assertEquals(
                (fetchNewRecipeResource as BaseResource.Error).errorMessage,
                "Network error while fetching"
            )
        }
    }
}