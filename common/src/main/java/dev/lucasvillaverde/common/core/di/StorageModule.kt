package dev.lucasvillaverde.common.core.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.lucasvillaverde.common.core.local.RecipeDatabase
import dev.lucasvillaverde.common.core.local.dao.FavoriteRecipesDao
import dev.lucasvillaverde.common.core.local.dao.RecipeDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StorageModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): RecipeDatabase {
        return Room
            .databaseBuilder(
                appContext.applicationContext,
                RecipeDatabase::class.java,
                "meals"
            )
            .build()
    }

    @Provides
    fun provideFavoriteRecipeDao(recipeDatabase: RecipeDatabase): FavoriteRecipesDao =
        recipeDatabase.favoriteRecipesDao

    @Provides
    fun provideRecipeDao(recipeDatabase: RecipeDatabase): RecipeDao {
        return recipeDatabase.recipeDao
    }
}