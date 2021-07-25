package dev.lucasvillaverde.recipeapp.feature_favorite_recipes

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import dev.lucasvillaverde.recipeapp.core.data.local.RecipeDatabase
import dev.lucasvillaverde.favorite_recipes.data.FavoriteRecipesRepositoryImpl
import dev.lucasvillaverde.favorite_recipes.data.local.FavoriteRecipesDao
import dev.lucasvillaverde.favorite_recipes.domain.repositories.FavoriteRecipesRepository
import dev.lucasvillaverde.favorite_recipes.domain.usecases.GetFavoriteRecipesUseCase
import dev.lucasvillaverde.favorite_recipes.domain.usecases.RemoveRecipeFromFavoriteUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FavoriteRecipesAppModule {
    @Provides
    fun provideFavoriteRecipeDao(recipeDatabase: RecipeDatabase): dev.lucasvillaverde.favorite_recipes.data.local.FavoriteRecipesDao =
        recipeDatabase.favoriteRecipesDao

    @Provides
    @Singleton
    fun provideFavoriteRecipesRepository(
        favoriteRecipesDao: dev.lucasvillaverde.favorite_recipes.data.local.FavoriteRecipesDao,
    ): dev.lucasvillaverde.favorite_recipes.domain.repositories.FavoriteRecipesRepository =
        dev.lucasvillaverde.favorite_recipes.data.FavoriteRecipesRepositoryImpl(favoriteRecipesDao)
}

@Module
@InstallIn(ViewModelComponent::class)
object FavoriteRecipesUseCaseModule {
    @Provides
    fun provideGetFavoriteRecipesUseCase(
        favoriteRecipesRepository: dev.lucasvillaverde.favorite_recipes.domain.repositories.FavoriteRecipesRepository
    ): dev.lucasvillaverde.favorite_recipes.domain.usecases.GetFavoriteRecipesUseCase =
        dev.lucasvillaverde.favorite_recipes.domain.usecases.GetFavoriteRecipesUseCase(
            favoriteRecipesRepository
        )

    @Provides
    fun provideRemoveRecipeFromFavoriteUseCase(
        favoriteRecipesRepository: dev.lucasvillaverde.favorite_recipes.domain.repositories.FavoriteRecipesRepository
    ): dev.lucasvillaverde.favorite_recipes.domain.usecases.RemoveRecipeFromFavoriteUseCase =
        dev.lucasvillaverde.favorite_recipes.domain.usecases.RemoveRecipeFromFavoriteUseCase(
            favoriteRecipesRepository
        )
}