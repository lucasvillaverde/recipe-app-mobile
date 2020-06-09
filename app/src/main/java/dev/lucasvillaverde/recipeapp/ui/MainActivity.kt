package dev.lucasvillaverde.recipeapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import dev.lucasvillaverde.recipeapp.R
import dev.lucasvillaverde.recipeapp.data.local.entities.MealEntity
import dev.lucasvillaverde.recipeapp.ui.adapters.MealAdapter
import dev.lucasvillaverde.recipeapp.viewmodels.MainViewModel
import dev.lucasvillaverde.recipeapp.viewmodels.MealDetailsViewModel
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
        mainViewModel.getMeals().observe(this, Observer {
            populateRecyclerView(it)
        })
    }

    private fun populateRecyclerView(mealList: List<MealEntity>) {
        mealRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = MealAdapter(mealList)
        }
    }
}
