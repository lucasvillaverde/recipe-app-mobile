package dev.lucasvillaverde.recipeapp.feature_recipe

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import dev.lucasvillaverde.recipeapp.feature_recipe.data.RecipeRepositoryImpl
import dev.lucasvillaverde.recipeapp.feature_recipe.data.local.dao.RecipeDao
import dev.lucasvillaverde.recipeapp.feature_recipe.data.remote.services.RecipeService
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.repositories.RecipeRepository
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.usecases.RecipeListUseCase
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.usecases.RecipeListUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RecipeRepositoryModule {
    @Provides
    @Singleton
    fun provideMealRepository(
        recipeDao: RecipeDao,
        recipeService: RecipeService
    ): RecipeRepository = RecipeRepositoryImpl(recipeDao, recipeService)
}

@Module
@InstallIn(ViewModelComponent::class)
object RecipeUseCaseModule {
    @Provides
    fun provideRecipeListUseCase(recipeRepository: RecipeRepository): RecipeListUseCase =
        RecipeListUseCaseImpl(recipeRepository)
}