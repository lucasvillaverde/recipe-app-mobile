package dev.lucasvillaverde.recipeapp.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dev.lucasvillaverde.recipeapp.data.local.dao.MealDAO
import dev.lucasvillaverde.recipeapp.data.remote.services.MealService
import dev.lucasvillaverde.recipeapp.data.repositories.MealRepository

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {
    @Provides
    @ViewModelScoped
    fun provideMealRepository(mealDao: MealDAO, mealService: MealService) = MealRepository(mealDao, mealService)
}