package dev.lucasvillaverde.recipeapp.feature_favorite_recipes.presenter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.lucasvillaverde.recipeapp.base.presenter.MainActivity
import dev.lucasvillaverde.recipeapp.databinding.FragmentFavoriteRecipesBinding
import dev.lucasvillaverde.recipeapp.feature_favorite_recipes.presenter.adapter.FavoriteRecipesAdapter

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

        adapter = FavoriteRecipesAdapter()
        binding.rvFavoriteRecipes.adapter = adapter
        binding.rvFavoriteRecipes.layoutManager = LinearLayoutManager(requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteRecipesViewModel.recipes.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
}