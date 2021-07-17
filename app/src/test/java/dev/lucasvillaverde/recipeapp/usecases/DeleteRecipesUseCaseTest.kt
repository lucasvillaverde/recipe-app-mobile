package dev.lucasvillaverde.recipeapp.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dev.lucasvillaverde.recipeapp.base.data.model.BaseResource
import dev.lucasvillaverde.recipeapp.base.domain.None
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.repositories.RecipeRepository
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.usecases.DeleteRecipesUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class DeleteRecipesUseCaseTest {
    private val fakeRecipeRepository = mockk<RecipeRepository>()
    private val deleteRecipeUseCase = DeleteRecipesUseCase(fakeRecipeRepository)

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