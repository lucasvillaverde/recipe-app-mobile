package dev.lucasvillaverde.recipeapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import dev.lucasvillaverde.common.base.presenter.NavDirection
import dev.lucasvillaverde.common.base.presenter.Navigator
import dev.lucasvillaverde.recipeapp.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), Navigator {
    override lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setNavController()
        setupOnNavigationDestinationChanged()

        setContentView(binding.root)
    }

    private fun setNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun setupOnNavigationDestinationChanged() {
        navController.addOnDestinationChangedListener { _, _, _ ->
            supportActionBar?.title = ""
            supportActionBar?.subtitle = ""
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun navigateToDirection(navDirection: NavDirection) {
        when (navDirection) {
            is NavDirection.RecipeListToFavoriteRecipes -> navController.navigate(
                R.id.action_recipeListFragment_to_favoriteRecipesFragment
            )
            is NavDirection.RecipeListToRecipeDetails -> navController.navigate(
                R.id.action_recipeListFragment_to_recipeDetailsFragment,
                navDirection.bundle
            )
            is NavDirection.FavoriteRecipesToRecipeDetails -> navController.navigate(
                R.id.action_favoriteRecipesFragment_to_recipeDetailsFragment,
                navDirection.bundle
            )
        }
    }
}
