package dev.lucasvillaverde.recipeapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.lucasvillaverde.recipeapp.data.local.dao.MealDAO
import dev.lucasvillaverde.recipeapp.data.local.entities.MealEntity

@Database(entities = [MealEntity::class], version = 1)
abstract class MealDatabase : RoomDatabase() {
    abstract val mealDAO: MealDAO
}

private lateinit var INSTANCE: MealDatabase

fun getDatabase(context: Context): MealDatabase {
    synchronized(MealDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room
                .databaseBuilder(
                    context.applicationContext,
                    MealDatabase::class.java,
                    "meals"
                )
                .build()
        }
    }

    return INSTANCE
}