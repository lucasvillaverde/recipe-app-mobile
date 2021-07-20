package dev.lucasvillaverde.recipeapp.feature_recipe

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import dev.lucasvillaverde.recipeapp.core.data.local.RecipeDatabase
import dev.lucasvillaverde.recipeapp.feature_recipe.data.RecipeRepositoryImpl
import dev.lucasvillaverde.recipeapp.feature_recipe.data.local.RecipeDao
import dev.lucasvillaverde.recipeapp.feature_recipe.data.remote.services.RecipeService
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.repositories.RecipeRepository
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.usecases.*
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RecipeAppModule {
    @Provides
    fun provideRecipeDao(recipeDatabase: RecipeDatabase): RecipeDao {
        return recipeDatabase.recipeDao
    }

    @Provides
    fun provideRecipeService(retrofit: Retrofit): RecipeService {
        return retrofit.create(RecipeService::class.java)
    }

    @Provides
    @Singleton
    fun provideRecipeRepository(
        recipeDao: RecipeDao,
        recipeService: RecipeService
    ): RecipeRepository = RecipeRepositoryImpl(recipeDao, recipeService)
}

@Module
@InstallIn(ViewModelComponent::class)
object RecipeUseCaseModule {
    @Provides
    fun provideDeleteRecipesUseCase(recipeRepository: RecipeRepository): DeleteRecipesUseCase =
        DeleteRecipesUseCase(recipeRepository)

    @Provides
    fun provideFetchNewRecipeUseCase(recipeRepository: RecipeRepository): FetchNewRecipeUseCase =
        FetchNewRecipeUseCase(recipeRepository)

    @Provides
    fun provideGetRecipeListUseCase(recipeRepository: RecipeRepository): GetRecipeListUseCase =
        GetRecipeListUseCase(recipeRepository)

    @Provides
    fun provideGetRecipeUseCase(recipeRepository: RecipeRepository): GetRecipeUseCase =
        GetRecipeUseCase(recipeRepository)

    @Provides
    fun provideMarkRecipeAsFavoriteUseCase(recipeRepository: RecipeRepository): ToggleRecipeIsFavorite =
        ToggleRecipeIsFavorite(recipeRepository)
}