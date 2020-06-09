package dev.lucasvillaverde.recipeapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.lucasvillaverde.recipeapp.R

class MealDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal_details)
    }
}