package dev.lucasvillaverde.recipeapp.feature_recipe.presenter.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.lucasvillaverde.recipeapp.R
import dev.lucasvillaverde.recipeapp.base.presenter.MainActivity
import dev.lucasvillaverde.recipeapp.databinding.FragmentRecipeListBinding
import dev.lucasvillaverde.recipeapp.feature_recipe.data.local.model.RecipeEntity
import dev.lucasvillaverde.recipeapp.feature_recipe.presenter.recipe_details.RecipeDetailsFragment.Companion.MEAL_ID
import dev.lucasvillaverde.recipeapp.feature_recipe.presenter.recipe_list.adapter.RecipeAdapter
import dev.lucasvillaverde.recipeapp.utils.DeviceUtils

@AndroidEntryPoint
class RecipeListFragment : Fragment() {
    lateinit var binding: FragmentRecipeListBinding

    private val recipeListViewModel: RecipeListViewModel by viewModels()
    private lateinit var recipeAdapter: RecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).supportActionBar?.hide()
        binding = FragmentRecipeListBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupMealRecyclerView()
        setupOnClickListeners()
        setupObserver()
        setupSearchView()
    }

    private fun setupMealRecyclerView() {
        recipeAdapter = RecipeAdapter()

        binding.mealRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = recipeAdapter
            visibility = View.GONE
        }

        recipeAdapter.onItemClick = {
            findNavController().navigate(
                R.id.action_recipeListFragment_to_recipeDetailsFragment,
                bundleOf(MEAL_ID to it.id)
            )
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
                recipeListViewModel.pageState.value?.data?.let { list ->
                    if (text.isNullOrEmpty()) recipeAdapter.submitList(list)
                    else recipeAdapter.submitList(list.filter { it.name!!.contains(text, true) })
                }

                return true
            }
        })
    }

    private fun setupOnClickListeners() {
        binding.btnGetMeal.setOnClickListener {
            if (DeviceUtils.hasInternet(requireContext().applicationContext))
                recipeListViewModel.getNewMeal()
            else
                Toast.makeText(
                    requireActivity(),
                    getString(R.string.no_internet_error),
                    Toast.LENGTH_LONG
                ).show()
        }

        binding.btnDeleteMeals.setOnClickListener {
            recipeListViewModel.deleteMeals()
        }
    }

    private fun setupObserver() {
        recipeListViewModel.pageState.observe(viewLifecycleOwner) {
            setPageLoading(it.isLoading)

            it.data?.let { data ->
                populateRecyclerView(data)
                updateUI(data)
            }

            if (it.isError) {
                Toast.makeText(
                    requireActivity(),
                    getString(R.string.network_error),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun populateRecyclerView(recipeList: List<RecipeEntity>) {
        recipeAdapter.submitList(recipeList)
    }

    private fun updateUI(listRecipe: List<RecipeEntity>) {
        when {
            listRecipe.isNotEmpty() -> {
                binding.imgEmptyState.visibility = View.GONE
                binding.tvEmptyState.text = getString(R.string.check_some_meal)
                binding.mealRecyclerView.visibility = View.VISIBLE
            }
            else -> {
                binding.mealRecyclerView.visibility = View.GONE
                binding.imgEmptyState.visibility = View.VISIBLE
                binding.tvEmptyState.text = getString(R.string.hit_btn_get_some_meals)
            }
        }
    }

    private fun setPageLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.btnGetMeal.alpha = 0.2F
            binding.btnDeleteMeals.alpha = 0.2F
            binding.btnGetMeal.isClickable = false
            binding.btnDeleteMeals.isClickable = false
            binding.imgEmptyState.visibility = View.GONE
            binding.tvEmptyState.visibility = View.GONE
            binding.loader.visibility = View.VISIBLE
        } else {
            binding.loader.visibility = View.GONE
            binding.btnGetMeal.alpha = 1F
            binding.btnDeleteMeals.alpha = 1F
            binding.btnGetMeal.isClickable = true
            binding.btnDeleteMeals.isClickable = true
            binding.imgEmptyState.visibility = View.VISIBLE
            binding.tvEmptyState.visibility = View.VISIBLE
        }
    }
}