package dev.lucasvillaverde.recipeapp.feature_recipe.presenter.recipe_list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.lucasvillaverde.recipeapp.R
import dev.lucasvillaverde.recipeapp.databinding.FragmentRecipeListBinding
import dev.lucasvillaverde.recipeapp.feature_recipe.data.local.entities.MealEntity
import dev.lucasvillaverde.recipeapp.feature_recipe.presenter.adapters.MealAdapter
import dev.lucasvillaverde.recipeapp.feature_recipe.presenter.recipe_details.MealDetailsActivity
import dev.lucasvillaverde.recipeapp.utils.DeviceUtils

@AndroidEntryPoint
class RecipeListFragment : Fragment() {
    lateinit var binding: FragmentRecipeListBinding

    private val mainViewModel: RecipeListViewModel by viewModels()
    private lateinit var mealAdapter: MealAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeListBinding.inflate(layoutInflater)
        setupMealRecyclerView()
        setupOnClickListeners()
        setupObservers()
        setupSearchView()

        return binding.root
    }

    private fun setupMealRecyclerView() {
        mealAdapter = MealAdapter()

        binding.mealRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = mealAdapter
            visibility = View.GONE
        }

        mealAdapter.onItemClick = {
            val intent = Intent(requireActivity(), MealDetailsActivity::class.java).apply {
                putExtra("MEAL_ID", it.id)
            }
            startActivity(intent)
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

    private fun setupOnClickListeners() {
        binding.btnGetMeal.setOnClickListener {
            if (DeviceUtils.hasInternet(requireContext().applicationContext))
                mainViewModel.getNewMeal()
            else
                Toast.makeText(
                    requireActivity(),
                    getString(R.string.no_internet_error),
                    Toast.LENGTH_LONG
                ).show()
        }

        binding.btnDeleteMeals.setOnClickListener {
            mainViewModel.deleteMeals()
        }
    }

    private fun setupObservers() {
        mainViewModel.isLoading.observe(viewLifecycleOwner, {
            setPageLoading(it)
        })

        mainViewModel.networkError.observe(viewLifecycleOwner, {
            if (it) {
                Toast.makeText(
                    requireActivity(),
                    getString(R.string.network_error),
                    Toast.LENGTH_LONG
                ).show()
            }
        })

        mainViewModel.getMeals().observe(viewLifecycleOwner, {
            updateUI(it)
            populateRecyclerView(it)
        })
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
}