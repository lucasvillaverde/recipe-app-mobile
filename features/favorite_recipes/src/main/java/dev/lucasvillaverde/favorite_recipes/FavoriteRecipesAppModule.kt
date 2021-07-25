package dev.lucasvillaverde.recipeapp.feature_favorite_recipes

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import dev.lucasvillaverde.common.core.local.RecipeDatabase
import dev.lucasvillaverde.common.core.local.dao.FavoriteRecipesDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FavoriteRecipesAppModule {


    @Provides
    @Singleton
    fun provideFavoriteRecipesRepository(
        favoriteRecipesDao: FavoriteRecipesDao,
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