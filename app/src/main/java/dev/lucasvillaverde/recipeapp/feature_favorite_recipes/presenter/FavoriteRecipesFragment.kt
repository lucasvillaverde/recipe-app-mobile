package dev.lucasvillaverde.recipeapp.feature_favorite_recipes.presenter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.lucasvillaverde.recipeapp.R
import dev.lucasvillaverde.recipeapp.base.presenter.MainActivity
import dev.lucasvillaverde.recipeapp.databinding.FragmentFavoriteRecipesBinding
import dev.lucasvillaverde.recipeapp.feature_favorite_recipes.presenter.adapter.FavoriteRecipesAdapter
import dev.lucasvillaverde.recipeapp.feature_recipe.presenter.recipe_details.RecipeDetailsFragment

@AndroidEntryPoint
class FavoriteRecipesFragment : Fragment() {
    lateinit var binding: FragmentFavoriteRecipesBinding
    lateinit var adapter: FavoriteRecipesAdapter
    private val favoriteRecipesViewModel: FavoriteRecipesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).supportActionBar?.show()
        binding = FragmentFavoriteRecipesBinding.inflate(inflater)
        setupUI()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteRecipesViewModel.favoriteRecipes.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun setupUI() {
        (activity as MainActivity).supportActionBar?.title = "Favorite Recipes"

        adapter = FavoriteRecipesAdapter(
            onItemClick = { openRecipeDetails(it) }
        )
        binding.rvFavoriteRecipes.adapter = adapter
        binding.rvFavoriteRecipes.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun openRecipeDetails(id: Int) {
        findNavController()
            .navigate(
                R.id.action_favoriteRecipesFragment_to_recipeDetailsFragment,
                bundleOf(Pair(RecipeDetailsFragment.MEAL_ID, id))
            )
    }
}