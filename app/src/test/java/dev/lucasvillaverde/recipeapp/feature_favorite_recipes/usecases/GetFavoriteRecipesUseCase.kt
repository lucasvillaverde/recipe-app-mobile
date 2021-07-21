package dev.lucasvillaverde.recipeapp.feature_favorite_recipes.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dev.lucasvillaverde.recipeapp.core.data.local.model.RecipeEntity
import dev.lucasvillaverde.recipeapp.feature_favorite_recipes.domain.FavoriteRecipeMapper
import dev.lucasvillaverde.recipeapp.feature_favorite_recipes.domain.repositories.FavoriteRecipesRepository
import dev.lucasvillaverde.recipeapp.feature_favorite_recipes.domain.usecases.GetFavoriteRecipesUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Rule
import org.junit.Test

class GetFavoriteRecipesUseCaseTest {
    private val fakeFavoriteRecipesRepository = mockk<FavoriteRecipesRepository>()
    private val getFavoriteRecipesUseCase = GetFavoriteRecipesUseCase(fakeFavoriteRecipesRepository)

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

        every { fakeFavoriteRecipesRepository.getFavoriteRecipesFlow() } returns fakeFlow

        runBlocking {
            val firstList = getFavoriteRecipesUseCase.executeFlow().toList().first()
            assertEquals(firstList.first(), FavoriteRecipeMapper.mapFromEntity(fakeList.first()))
        }
    }

    @Test
    fun itShouldRetrieveFavoriteRecipesFailure() {
        val exception = Exception("Data error")
        every { fakeFavoriteRecipesRepository.getFavoriteRecipesFlow() } throws exception

        assertThrows(Exception::class.java) {
            runBlocking {
                getFavoriteRecipesUseCase.executeFlow().toList().first()
            }
        }
    }
}