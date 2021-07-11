package dev.lucasvillaverde.recipeapp.feature_recipe

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.lucasvillaverde.recipeapp.feature_recipe.data.local.dao.RecipeDao
import dev.lucasvillaverde.recipeapp.feature_recipe.data.remote.services.RecipeService
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.repositories.RecipeRepository
import dev.lucasvillaverde.recipeapp.feature_recipe.data.RecipeRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RecipeModule {
    @Provides
    @Singleton
    fun provideMealRepository(
        recipeDao: RecipeDao,
        recipeService: RecipeService
    ): RecipeRepository {
        return RecipeRepositoryImpl(recipeDao, recipeService)
    }
}