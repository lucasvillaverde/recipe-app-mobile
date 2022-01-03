package dev.lucasvillaverde.recipes.presenter.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.lucasvillaverde.common.base.presenter.BaseFragment
import dev.lucasvillaverde.common.base.presenter.NavDirection
import dev.lucasvillaverde.recipes.R
import dev.lucasvillaverde.recipes.databinding.FragmentRecipeListBinding
import dev.lucasvillaverde.recipes.presenter.recipe_list.components.RecipeListScreen

@AndroidEntryPoint
class RecipeListFragment : BaseFragment() {
    lateinit var binding: FragmentRecipeListBinding

    private val recipeListViewModel: RecipeListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        actionBar?.hide()
        binding = FragmentRecipeListBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecipeListComposable()
        setupOnClickListeners()
        setupObserver()
        setupSearchView()
    }

    private fun setupRecipeListComposable() {
        binding.cvRecipeList.apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner)
            )

            setContent {
                MaterialTheme {
                    RecipeListScreen(
                        onRecipeClick = { recipeId ->
                            navigateToRecipeDetails(recipeId)
                        }
                    )
                }
            }
        }

    }

    private fun navigateToRecipeDetails(recipeId: Int) {
        navigator.navigateToDirection(
            NavDirection.RecipeListToRecipeDetails(
                bundleOf(
                    Pair(
                        NavDirection.RecipeListToRecipeDetails.recipeIdArgName,
                        recipeId
                    )
                )
            )
        )
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
                return true
            }
        })
    }

    private fun setupOnClickListeners() {
        binding.fabDeleteRecipes.setOnClickListener {
            recipeListViewModel.deleteMeals()
        }

        binding.fabFavoriteRecipes.setOnClickListener {
            navigator
                .navigateToDirection(NavDirection.RecipeListToFavoriteRecipes())
        }
    }

    private fun setupObserver() {
        recipeListViewModel.pageState.observe(viewLifecycleOwner) {
            setPageLoading(it.isLoading)

            if (it.isError) {
                Toast.makeText(
                    requireActivity(),
                    getString(R.string.network_error),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun setPageLoading(isLoading: Boolean) {
        binding.fabDeleteRecipes.isClickable = !isLoading
        binding.fabFavoriteRecipes.isClickable = !isLoading
    }
}