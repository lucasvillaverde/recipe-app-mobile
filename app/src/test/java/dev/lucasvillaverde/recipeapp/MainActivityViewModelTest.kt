package dev.lucasvillaverde.recipeapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dev.lucasvillaverde.recipeapp.repository.MealRepositoryTestImpl
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.MockitoAnnotations

class MainActivityViewModelTest {
    private val fakeMealRepository = MealRepositoryTestImpl()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun wasMealReturnedSuccessfully() {
        val mainViewModel = MainViewModel(fakeMealRepository)

        mainViewModel.getMeals().observeForTesting {
            assert(mainViewModel.getMeals().value!!.isNotEmpty())
        }
    }
}