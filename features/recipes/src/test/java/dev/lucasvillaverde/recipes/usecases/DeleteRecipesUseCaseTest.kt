package dev.lucasvillaverde.recipes.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dev.lucasvillaverde.common.base.model.BaseResource
import dev.lucasvillaverde.common.base.domain.None
import dev.lucasvillaverde.recipes.domain.repositories.RecipeRepository
import dev.lucasvillaverde.recipes.domain.usecases.DeleteRecipesUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class DeleteRecipesUseCaseTest {
    private val fakeRecipeRepository = mockk<dev.lucasvillaverde.recipes.domain.repositories.RecipeRepository>()
    private val deleteRecipeUseCase =
        dev.lucasvillaverde.recipes.domain.usecases.DeleteRecipesUseCase(fakeRecipeRepository)

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun itShouldDeleteRecipesSuccessfully() {
        coEvery { fakeRecipeRepository.deleteRecipes() } returns Unit

        runBlocking {
            val deletedRecipesResource = deleteRecipeUseCase.execute(None)
            assertTrue(deletedRecipesResource is BaseResource.Success)
        }
    }

    @Test
    fun itShouldDeleteRecipesFailure() {
        coEvery { fakeRecipeRepository.deleteRecipes() } throws Exception("Could not delete recipes")

        runBlocking {
            val deletedRecipesResource = deleteRecipeUseCase.execute(None)
            assertTrue(deletedRecipesResource is BaseResource.Error)
            assertEquals(
                (deletedRecipesResource as BaseResource.Error).errorMessage,
                "Could not delete recipes"
            )
        }
    }
}