package dev.lucasvillaverde.recipes

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import dev.lucasvillaverde.common.core.local.dao.RecipeDao
import dev.lucasvillaverde.recipes.data.remote.services.RecipeService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RecipeAppModule {
    @Provides
    fun provideRecipeService(retrofit: Retrofit): RecipeService {
        return retrofit.create(RecipeService::class.java)
    }

    @Provides
    @Singleton
    fun provideRecipeRepository(
        recipeDao: RecipeDao,
        recipeService: RecipeService
    ): dev.lucasvillaverde.recipes.domain.repositories.RecipeRepository =
        dev.lucasvillaverde.recipes.data.RecipeRepositoryImpl(recipeDao, recipeService)
}

@Module
@InstallIn(ViewModelComponent::class)
object RecipeUseCaseModule {
    @Provides
    fun provideDeleteRecipesUseCase(recipeRepository: dev.lucasvillaverde.recipes.domain.repositories.RecipeRepository): dev.lucasvillaverde.recipes.domain.usecases.DeleteRecipesUseCase =
        dev.lucasvillaverde.recipes.domain.usecases.DeleteRecipesUseCase(recipeRepository)

    @Provides
    fun provideFetchNewRecipeUseCase(recipeRepository: dev.lucasvillaverde.recipes.domain.repositories.RecipeRepository): dev.lucasvillaverde.recipes.domain.usecases.FetchNewRecipeUseCase =
        dev.lucasvillaverde.recipes.domain.usecases.FetchNewRecipeUseCase(recipeRepository)

    @Provides
    fun provideGetRecipeListUseCase(recipeRepository: dev.lucasvillaverde.recipes.domain.repositories.RecipeRepository): dev.lucasvillaverde.recipes.domain.usecases.GetRecipeListUseCase =
        dev.lucasvillaverde.recipes.domain.usecases.GetRecipeListUseCase(recipeRepository)

    @Provides
    fun provideGetRecipeUseCase(recipeRepository: dev.lucasvillaverde.recipes.domain.repositories.RecipeRepository): dev.lucasvillaverde.recipes.domain.usecases.GetRecipeUseCase =
        dev.lucasvillaverde.recipes.domain.usecases.GetRecipeUseCase(recipeRepository)

    @Provides
    fun provideMarkRecipeAsFavoriteUseCase(recipeRepository: dev.lucasvillaverde.recipes.domain.repositories.RecipeRepository): dev.lucasvillaverde.recipes.domain.usecases.ToggleRecipeIsFavorite =
        dev.lucasvillaverde.recipes.domain.usecases.ToggleRecipeIsFavorite(recipeRepository)
}