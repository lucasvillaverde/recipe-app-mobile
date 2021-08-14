package dev.lucasvillaverde.favorite_recipes

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import dev.lucasvillaverde.common.core.local.dao.FavoriteRecipesDao
import dev.lucasvillaverde.favorite_recipes.data.FavoriteRecipesRepositoryImpl
import dev.lucasvillaverde.favorite_recipes.domain.repositories.FavoriteRecipesRepository
import dev.lucasvillaverde.favorite_recipes.domain.usecases.GetFavoriteRecipesUseCase
import dev.lucasvillaverde.favorite_recipes.domain.usecases.RemoveRecipeFromFavoriteUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FavoriteRecipesAppModule {
    @Provides
    @Singleton
    fun provideFavoriteRecipesRepository(
        favoriteRecipesDao: FavoriteRecipesDao,
    ): FavoriteRecipesRepository =
        FavoriteRecipesRepositoryImpl(favoriteRecipesDao)
}

@Module
@InstallIn(ViewModelComponent::class)
object FavoriteRecipesUseCaseModule {
    @Provides
    fun provideGetFavoriteRecipesUseCase(
        favoriteRecipesRepository: FavoriteRecipesRepository
    ): GetFavoriteRecipesUseCase =
        GetFavoriteRecipesUseCase(
            favoriteRecipesRepository
        )

    @Provides
    fun provideRemoveRecipeFromFavoriteUseCase(
        favoriteRecipesRepository: FavoriteRecipesRepository
    ): RemoveRecipeFromFavoriteUseCase =
        RemoveRecipeFromFavoriteUseCase(
            favoriteRecipesRepository
        )
}