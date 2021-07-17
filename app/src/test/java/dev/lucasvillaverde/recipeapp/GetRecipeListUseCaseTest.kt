package dev.lucasvillaverde.recipeapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dev.lucasvillaverde.recipeapp.base.data.model.BaseResource
import dev.lucasvillaverde.recipeapp.base.domain.None
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.usecases.GetRecipeListUseCase
import dev.lucasvillaverde.recipeapp.repository.RecipeRepositoryTestImpl
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.MockitoAnnotations

class GetRecipeListUseCaseTest {
    private val fakeRecipeRepository = RecipeRepositoryTestImpl()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun itShouldReturnRecipeListSuccessfully() {
        val getListUseCase = GetRecipeListUseCase(fakeRecipeRepository)

        runBlocking {
            val recipeListResource = getListUseCase.execute(None)
            assertTrue(recipeListResource is BaseResource.Success)
            assertNotNull(recipeListResource.data)
            assertEquals(recipeListResource.data!!.size, 1)
        }
    }
}