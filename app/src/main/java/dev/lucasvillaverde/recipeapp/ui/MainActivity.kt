package dev.lucasvillaverde.recipeapp.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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

    private fun updateUI(listMeal: List<MealEntity>) {
        if (listMeal.isNotEmpty()) {
            binding.imgNoMeal.visibility = View.GONE
            binding.tvTitleDraw.text = getString(R.string.check_some_meal)
            binding.mealRecyclerView.visibility = View.VISIBLE

            return
        }

        binding.mealRecyclerView.visibility = View.GONE
        binding.imgNoMeal.visibility = View.VISIBLE
        binding.tvTitleDraw.text = getString(R.string.hit_btn_get_some_meals)
    }

    private fun setPageLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.btnGetMeal.alpha = 0.2F
            binding.btnDeleteMeals.alpha = 0.2F
            binding.btnGetMeal.isClickable = false
            binding.btnDeleteMeals.isClickable = false
            binding.loader.visibility = View.VISIBLE
        } else {
            binding.loader.visibility = View.GONE
            binding.btnGetMeal.alpha = 1F
            binding.btnDeleteMeals.alpha = 1F
            binding.btnGetMeal.isClickable = true
            binding.btnDeleteMeals.isClickable = true
        }
    }

    private fun setupObservers() {
        mainViewModel.isLoading.observe(this, {
            setPageLoading(it)
        })

        mainViewModel.networkError.observe(this, {
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

        mainViewModel.getMeals().observe(this, {
            updateUI(it)
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
                mainViewModel.getMeals().value?.let { list ->
                    if (text.isNullOrEmpty()) mealAdapter.submitList(list)
                    else mealAdapter.submitList(list.filter { it.name!!.contains(text, true) })
                }

                return true
            }
        })
    }

    private fun setupMealRecyclerView() {
        mealAdapter = MealAdapter()

        binding.mealRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = mealAdapter
            visibility = View.GONE
        }

        mealAdapter.onItemClick = {
            val intent = Intent(this, MealDetailsActivity::class.java).apply {
                putExtra("MEAL_ID", it.id)
            }
            startActivity(intent)
        }
    }
}
