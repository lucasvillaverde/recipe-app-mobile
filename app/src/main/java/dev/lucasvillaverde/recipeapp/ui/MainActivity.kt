package dev.lucasvillaverde.recipeapp.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
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
    private var mealAdapter = MealAdapter(listOf())
    private var mealList = listOf<MealEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        mealRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = mealAdapter
        }

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

        mainViewModel.networkError.observe(this, Observer {
            if (it) {
                Toast.makeText(
                    this,
                    getString(R.string.network_error),
                    Toast.LENGTH_LONG
                ).show()
                Handler().postDelayed({
                    mainViewModel.networkError.value = false
                    finish()
                    startActivity(intent)
                }, 3000)
            }
        })

        mainViewModel.getMeals().observe(this, Observer {
            mealList = it
            updateUI()
            populateRecyclerView()
        })

        svRecipes.setOnClickListener {
            svRecipes.isIconified = false
        }

        svRecipes.setOnQueryTextFocusChangeListener { _, isFocused ->
            svRecipes.isIconified = !isFocused
        }

        svRecipes.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean = true

            override fun onQueryTextChange(text: String?): Boolean {
                Log.d("TEXT", "CHANGED: $text")
                mealAdapter.filter(text)
                return true
            }
        })
    }

    private fun populateRecyclerView() {
        mealAdapter.update(mealList)
        mealAdapter.onItemClick = {
            val intent = Intent(this, MealDetailsActivity::class.java).apply {
                putExtra("MEAL_ID", it.id)
            }

            startActivity(intent)
        }
    }

    private fun updateUI() {
        tvTitleDraw.text =
            if (mealList.isNotEmpty()) getString(R.string.check_some_meal) else getString(
                R.string.hit_btn_get_some_meals
            )
    }

    private fun setPageLoading(status: Boolean) {
        if (status) {
            rootScrollView.alpha = 0.2F
            btnGetMeal.alpha = 0.2F
            btnDeleteMeals.alpha = 0.2F
            rootScrollView.setAllEnabled(false)
            btnGetMeal.isClickable = false
            btnDeleteMeals.isClickable = false
            loader.visibility = View.VISIBLE
        } else {
            loader.visibility = View.GONE
            rootScrollView.alpha = 1F
            btnGetMeal.alpha = 1F
            btnDeleteMeals.alpha = 1F
            rootScrollView.setAllEnabled(true)
            btnGetMeal.isClickable = true
            btnDeleteMeals.isClickable = true
        }
    }

    fun View.setAllEnabled(enabled: Boolean) {
        isEnabled = enabled
        if (this is ViewGroup) children.forEach { child -> child.setAllEnabled(enabled) }
    }
}
