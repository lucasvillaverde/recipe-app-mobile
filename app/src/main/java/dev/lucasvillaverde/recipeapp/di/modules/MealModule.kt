package dev.lucasvillaverde.recipeapp.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.lucasvillaverde.recipeapp.data.local.dao.MealDAO
import dev.lucasvillaverde.recipeapp.data.remote.services.MealService
import dev.lucasvillaverde.recipeapp.data.repositories.MealRepository
import dev.lucasvillaverde.recipeapp.domain.repositories.MealRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MealModule {
    @Provides
    @Singleton
    fun provideMealRepository(
        mealDAO: MealDAO,
        mealService: MealService
    ): MealRepository {
        return MealRepositoryImpl(mealDAO, mealService)
    }
}