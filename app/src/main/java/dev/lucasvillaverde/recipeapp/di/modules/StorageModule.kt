package dev.lucasvillaverde.recipeapp.di.modules

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.lucasvillaverde.recipeapp.data.local.MealDatabase
import dev.lucasvillaverde.recipeapp.data.local.dao.MealDAO
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StorageModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): MealDatabase {
        return Room
            .databaseBuilder(
                appContext.applicationContext,
                MealDatabase::class.java,
                "meals"
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideMealDAO(mealDatabase: MealDatabase): MealDAO {
        return mealDatabase.mealDAO
    }
}