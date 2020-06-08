package dev.lucasvillaverde.recipeapp

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import dev.lucasvillaverde.recipeapp.data.local.entities.MealEntity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by lazy {
        val application = requireNotNull(application) {
            "Application must not be null!"
        }
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModel.meals.observe(this, Observer { it ->
            Log.d("MEAL_ACTIVITY", "MEAL OBSERVER, ITEM CHANGED")
        })

        btnRefreshMeal.setOnClickListener(View.OnClickListener {
            mainViewModel.getRandomMeal()?.let { meal ->
                updateMealCard(meal)
            }
        })
    }

    private fun updateMealCard(meal: MealEntity) {
        Picasso.get().load(meal.thumb).into(imgMeal);
        txtMealTitle.text = meal.name
        txtMealDescription.text = meal.region
        txtInstructions.text = meal.instructions
    }
}
