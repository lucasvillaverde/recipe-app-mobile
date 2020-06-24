package dev.lucasvillaverde.recipeapp.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.lucasvillaverde.recipeapp.R
import dev.lucasvillaverde.recipeapp.data.local.entities.MealEntity
import dev.lucasvillaverde.recipeapp.ui.adapters.MealAdapter
import dev.lucasvillaverde.recipeapp.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnGetMeal.setOnClickListener {
            if (mainViewModel.hasInternet())
                mainViewModel.getNewMeal()
            else
                Toast.makeText(
                    this,
                    getString(R.string.no_internet_error),
                    Toast.LENGTH_LONG
                ).show()
        }

        btnDeleteMeals.setOnClickListener {
            mainViewModel.deleteMeals()
        }

        mainViewModel.isLoading.observe(this, Observer {
            setPageLoading(it)
        })

        mainViewModel._networkError.observe(this, Observer {
            if(it) {
                Toast.makeText(
                    this,
                    getString(R.string.network_error),
                    Toast.LENGTH_LONG
                ).show()
                Handler().postDelayed({
                    mainViewModel._networkError.value = false
                    finish()
                    startActivity(intent)
                }, 3000)
            }
        })

        mainViewModel.getMeals().observe(this, Observer {
            updateUI(it)
            populateRecyclerView(it)
        })
    }

    private fun populateRecyclerView(mealList: List<MealEntity>) {
        val mealAdapter = MealAdapter(mealList)
        mealAdapter.onItemClick = {
            val intent = Intent(this, MealDetailsActivity::class.java).apply {
                putExtra("MEAL_ID", it.id)
            }

            startActivity(intent)
        }

        mealRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mealAdapter
        }
    }

    private fun updateUI(it: List<MealEntity>) {
        tvTitleDraw.text =
            if (it.isNotEmpty()) getString(R.string.checkout_some_meal) else getString(
                R.string.hit_btn_get_some_meals
            )
    }

    private fun setPageLoading(status: Boolean) {
        if (status) {
            rootScrollView.alpha = 0.2F
            loader.visibility = View.VISIBLE
        } else {
            loader.visibility = View.GONE
            rootScrollView.alpha = 1F
        }


    }
}
