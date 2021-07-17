package dev.lucasvillaverde.recipeapp.feature_recipe

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import dev.lucasvillaverde.recipeapp.feature_recipe.data.RecipeRepositoryImpl
import dev.lucasvillaverde.recipeapp.feature_recipe.data.local.RecipeDatabase
import dev.lucasvillaverde.recipeapp.feature_recipe.data.local.dao.RecipeDao
import dev.lucasvillaverde.recipeapp.feature_recipe.data.remote.services.RecipeService
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.repositories.RecipeRepository
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.usecases.DeleteRecipesUseCase
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.usecases.FetchNewRecipeUseCase
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.usecases.GetRecipeListUseCase
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.usecases.GetRecipeUseCase
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RecipeRepositoryModule {
    @Provides
    fun provideMealDAO(recipeDatabase: RecipeDatabase): RecipeDao {
        return recipeDatabase.recipeDao
    }

    @Provides
    fun provideMealService(retrofit: Retrofit): RecipeService {
        return retrofit.create(RecipeService::class.java)
    }

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
}