package dev.lucasvillaverde.favorite_recipes.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dev.lucasvillaverde.common.base.domain.None
import dev.lucasvillaverde.common.base.model.BaseResource
import dev.lucasvillaverde.common.core.local.model.RecipeEntity
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class GetFavoriteRecipesUseCaseTest {
    private val fakeFavoriteRecipesRepository = mockk<dev.lucasvillaverde.favorite_recipes.domain.repositories.FavoriteRecipesRepository>()
    private val getFavoriteRecipesUseCase =
        dev.lucasvillaverde.favorite_recipes.domain.usecases.GetFavoriteRecipesUseCase(
            fakeFavoriteRecipesRepository
        )

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun itShouldRetrieveFavoriteRecipesSuccessfully() {
        val fakeList = listOf(
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
                true
            )
        )

        val fakeFlow = flowOf(fakeList)

        every { fakeFavoriteRecipesRepository.getFavoriteRecipes() } returns fakeFlow

        runBlocking {
            val favoriteRecipeResource = getFavoriteRecipesUseCase.execute(None).first()

            assertTrue(favoriteRecipeResource is BaseResource.Success)
            assertEquals(
                favoriteRecipeResource.data!!.first(),
                dev.lucasvillaverde.favorite_recipes.domain.FavoriteRecipeMapper.mapFromEntity(fakeList.first())
            )
        }
    }

    @Test
    fun itShouldRetrieveFavoriteRecipesFailure() {
        val exception = Exception("Data error")
        every { fakeFavoriteRecipesRepository.getFavoriteRecipes() } returns flow {
            throw exception
        }

        runBlocking {
            val favoriteRecipeResource = getFavoriteRecipesUseCase.execute(None).first()

            assertTrue(favoriteRecipeResource is BaseResource.Error)
            assertEquals(
                exception.message,
                (favoriteRecipeResource as BaseResource.Error).errorMessage
            )
        }
    }
}