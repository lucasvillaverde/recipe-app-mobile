package dev.lucasvillaverde.recipeapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import dev.lucasvillaverde.recipeapp.R
import dev.lucasvillaverde.recipeapp.data.local.entities.MealEntity
import dev.lucasvillaverde.recipeapp.viewmodels.MealDetailsViewModel
import kotlinx.android.synthetic.main.activity_meal_details.*

class MealDetailsActivity : AppCompatActivity() {

    private val mealDetailsViewModel: MealDetailsViewModel by lazy {
        val application = requireNotNull(application) {
            "Application must not be null!"
        }
        ViewModelProvider(this).get(MealDetailsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal_details)

        val mealId = intent.getIntExtra("MEAL_ID", 0)
        mealDetailsViewModel.getMealById(mealId).observe(this, Observer {
            updateMealCard(it)
        })

    }

    private fun updateMealCard(meal: MealEntity) {
        Picasso.get().load(meal.thumb).into(imgMeal);
        txtMealTitle.text = meal.name
        txtMealDescription.text = meal.region
        txtInstructions.text = meal.instructions
    }
}