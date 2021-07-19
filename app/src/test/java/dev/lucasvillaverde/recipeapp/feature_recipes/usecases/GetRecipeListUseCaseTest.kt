package dev.lucasvillaverde.recipeapp.feature_recipes.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dev.lucasvillaverde.recipeapp.base.data.model.BaseResource
import dev.lucasvillaverde.recipeapp.base.domain.None
import dev.lucasvillaverde.recipeapp.core.data.local.model.RecipeEntity
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.repositories.RecipeRepository
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.usecases.GetRecipeListUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class GetRecipeListUseCaseTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val fakeRecipeRepository = mockk<RecipeRepository>()
    private val getListUseCase = GetRecipeListUseCase(fakeRecipeRepository)

    @Test
    fun itShouldReturnRecipeListSuccessfully() {
        coEvery { fakeRecipeRepository.getRecipes() } returns mutableListOf(
            RecipeEntity(
                1,
                "Teste",
                "Test Category",
                "Region Test",
                "Test Instructions",
                null,
                null,
                null,
                mapOf(
                    Pair("Sweet potato", "2 pieces"),
                    Pair("Water", "2 cups")
                ),
                false
            )
        )

        runBlocking {
            val recipeListResource = getListUseCase.execute(None)
            assertTrue(recipeListResource is BaseResource.Success)
            assertNotNull(recipeListResource.data)
            assertEquals(recipeListResource.data!!.size, 1)
        }
    }

    @Test
    fun itShouldReturnEmptyRecipeListSuccessfully() {
        coEvery { fakeRecipeRepository.getRecipes() } returns mutableListOf()

        runBlocking {
            val recipeListResource = getListUseCase.execute(None)
            assertTrue(recipeListResource is BaseResource.Success)
            assertNotNull(recipeListResource.data)
            assertEquals(recipeListResource.data!!.size, 0)
        }
    }

    @Test
    fun itShouldReturnEmptyRecipeListFailure() {
        coEvery { fakeRecipeRepository.getRecipes() } throws Exception("Network error")

        runBlocking {
            val recipeListResource = getListUseCase.execute(None)
            assertTrue(recipeListResource is BaseResource.Error)
            assertNull(recipeListResource.data)
            assertEquals((recipeListResource as BaseResource.Error).errorMessage, "Network error")
        }
    }
}