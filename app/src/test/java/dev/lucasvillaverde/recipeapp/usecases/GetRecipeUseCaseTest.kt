package dev.lucasvillaverde.recipeapp.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dev.lucasvillaverde.recipeapp.base.data.model.BaseResource
import dev.lucasvillaverde.recipeapp.feature_recipe.data.local.model.RecipeEntity
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.repositories.RecipeRepository
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.usecases.GetRecipeUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class GetRecipeUseCaseTest {
    private val fakeRecipeRepository = mockk<RecipeRepository>()
    private val getRecipeUseCase = GetRecipeUseCase(fakeRecipeRepository)

    companion object {
        private const val DEFAULT_RECIPE_ID = 1
    }

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun itShouldReturnRecipeSuccessfully() {
        coEvery { fakeRecipeRepository.getRecipeById(DEFAULT_RECIPE_ID) } returns RecipeEntity(
            1,
            "Teste Recipe Entity",
            "Test Category",
            "Region Test",
            "Test Instructions",
            null,
            null,
            null,
            mapOf(
                Pair("Sweet potato", "2 pieces"),
                Pair("Water", "2 cups")
            )
        )

        runBlocking {
            val recipeListResource = getRecipeUseCase.execute(
                GetRecipeUseCase.Params(
                    DEFAULT_RECIPE_ID
                )
            )
            assertTrue(recipeListResource is BaseResource.Success)
            assertNotNull(recipeListResource.data)
            assertEquals(recipeListResource.data!!.id, 1)
        }
    }

    @Test
    fun itShouldReturnRecipeFailure() {
        coEvery { fakeRecipeRepository.getRecipeById(0) } throws Exception("Recipe not found")

        runBlocking {
            val recipeListResource = getRecipeUseCase.execute(GetRecipeUseCase.Params(0))
            assertTrue(recipeListResource is BaseResource.Error)
            assertNull(recipeListResource.data)
            assertEquals(
                (recipeListResource as BaseResource.Error).errorMessage,
                "Recipe not found"
            )
        }
    }
}