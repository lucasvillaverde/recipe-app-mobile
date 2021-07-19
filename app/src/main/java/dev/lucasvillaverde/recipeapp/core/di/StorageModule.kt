package dev.lucasvillaverde.recipeapp.core.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.lucasvillaverde.recipeapp.core.data.local.RecipeDatabase
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
}