package dev.lucasvillaverde.favorite_recipes.presenter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.lucasvillaverde.common.base.model.BaseResource
import dev.lucasvillaverde.common.base.presenter.BaseFragment
import dev.lucasvillaverde.common.base.presenter.NavDirection
import dev.lucasvillaverde.common.utils.AppConstants.MESSAGES.COMMON_ERROR_MESSAGE
import dev.lucasvillaverde.favorite_recipes.databinding.FragmentFavoriteRecipesBinding
import dev.lucasvillaverde.favorite_recipes.presenter.adapter.FavoriteRecipesAdapter

@AndroidEntryPoint
class FavoriteRecipesFragment : BaseFragment() {
    private lateinit var binding: FragmentFavoriteRecipesBinding
    lateinit var adapter: FavoriteRecipesAdapter
    private val favoriteRecipesViewModel: FavoriteRecipesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        actionBar?.show()
        binding = FragmentFavoriteRecipesBinding.inflate(inflater)
        setupUI()
        setupFavoriteRecipesObserver()

        return binding.root
    }

    private fun setupUI() {
        actionBar?.title = "Favorite Recipes"

        adapter = FavoriteRecipesAdapter(
            onRecipeItemClick = { openRecipeDetails(it) },
            onRemoveFromFavoriteClick = { removeRecipeFromFavorite(it) }
        )
        binding.rvFavoriteRecipes.adapter = adapter
        binding.rvFavoriteRecipes.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    private fun setupFavoriteRecipesObserver() {
        favoriteRecipesViewModel.favoriteRecipes.observe(viewLifecycleOwner) {
            when (it) {
                is BaseResource.Success -> adapter.submitList(it.data!!)
                is BaseResource.Error -> Toast.makeText(
                    requireActivity(),
                    COMMON_ERROR_MESSAGE,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun removeRecipeFromFavorite(id: Int) {
        favoriteRecipesViewModel.removeFromFavorite(id)
    }

    private fun openRecipeDetails(id: Int) {
        val navDirection = NavDirection.FavoriteRecipesToRecipeDetails(
            bundleOf(
                Pair(
                    NavDirection.FavoriteRecipesToRecipeDetails.recipeIdArgName, id
                )
            )
        )

        navigator.navigateToDirection(navDirection)
    }
}