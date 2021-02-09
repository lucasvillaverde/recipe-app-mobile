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
import dagger.hilt.android.AndroidEntryPoint
import dev.lucasvillaverde.recipeapp.R
import dev.lucasvillaverde.recipeapp.data.local.entities.MealEntity
import dev.lucasvillaverde.recipeapp.databinding.ActivityMainBinding
import dev.lucasvillaverde.recipeapp.ui.adapters.MealAdapter
import dev.lucasvillaverde.recipeapp.utils.DeviceUtils.hasInternet
import dev.lucasvillaverde.recipeapp.viewmodels.MainViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var mealAdapter: MealAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupMealRecyclerView()
        setupOnClickListeners()
        setupObservers()
        setupSearchView()
    }

    private fun populateRecyclerView(mealList: List<MealEntity>) {
        mealAdapter.submitList(mealList)
    }

    private fun updateUI() {
        binding.tvTitleDraw.text =
            if (mealAdapter.itemCount > 0) getString(R.string.check_some_meal) else getString(
                R.string.hit_btn_get_some_meals
            )
    }

    private fun setPageLoading(status: Boolean) {
        if (status) {
            binding.rootScrollView.alpha = 0.2F
            binding.btnGetMeal.alpha = 0.2F
            binding.btnDeleteMeals.alpha = 0.2F
            binding.rootScrollView.setAllEnabled(false)
            binding.btnGetMeal.isClickable = false
            binding.btnDeleteMeals.isClickable = false
            binding.loader.visibility = View.VISIBLE
        } else {
            binding.loader.visibility = View.GONE
            binding.rootScrollView.alpha = 1F
            binding.btnGetMeal.alpha = 1F
            binding.btnDeleteMeals.alpha = 1F
            binding.rootScrollView.setAllEnabled(true)
            binding.btnGetMeal.isClickable = true
            binding.btnDeleteMeals.isClickable = true
        }
    }

    private fun setupObservers() {
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

                Handler(mainLooper).postDelayed({
                    mainViewModel.networkError.value = false
                    finish()
                    startActivity(intent)
                }, 3000)
            }
        })

        mainViewModel.getMeals().observe(this, Observer {
            updateUI()
            populateRecyclerView(it)
        })
    }

    private fun setupOnClickListeners() {
        binding.btnGetMeal.setOnClickListener {
            if (hasInternet(applicationContext))
                mainViewModel.getNewMeal()
            else
                Toast.makeText(
                    this,
                    getString(R.string.no_internet_error),
                    Toast.LENGTH_LONG
                ).show()
        }

        binding.btnDeleteMeals.setOnClickListener {
            mainViewModel.deleteMeals()
        }
    }

    private fun setupSearchView() {
        binding.svRecipes.setOnClickListener {
            binding.svRecipes.isIconified = false
        }

        binding.svRecipes.setOnQueryTextFocusChangeListener { _, isFocused ->
            binding.svRecipes.isIconified = !isFocused
        }

        binding.svRecipes.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean = true

            override fun onQueryTextChange(text: String?): Boolean {
                Log.d("TEXT", "CHANGED: $text")
                mealAdapter.filter(text)
                return true
            }
        })
    }

    private fun setupMealRecyclerView() {
        mealAdapter = MealAdapter(listOf())

        binding.mealRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = mealAdapter
        }

        mealAdapter.onItemClick = {
            val intent = Intent(this, MealDetailsActivity::class.java).apply {
                putExtra("MEAL_ID", it.id)
            }
            startActivity(intent)
        }
    }

    fun View.setAllEnabled(enabled: Boolean) {
        isEnabled = enabled
        if (this is ViewGroup) children.forEach { child -> child.setAllEnabled(enabled) }
    }
}
