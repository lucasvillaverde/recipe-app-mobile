package dev.lucasvillaverde.recipe_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import dev.lucasvillaverde.common.base.presenter.NavScreenName
import dev.lucasvillaverde.favorite_recipes.presenter.FavoriteRecipeScreen
import dev.lucasvillaverde.favorite_recipes.presenter.FavoriteRecipesViewModel
import dev.lucasvillaverde.recipes.presenter.recipe_details.RecipeDetailsViewModel
import dev.lucasvillaverde.recipes.presenter.recipe_details.components.RecipeDetailsScreen
import dev.lucasvillaverde.recipes.presenter.recipe_list.RecipeListViewModel
import dev.lucasvillaverde.recipes.presenter.recipe_list.components.RecipeListScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = NavScreenName.RECIPE_LIST.value
            ) {
                composable(route = NavScreenName.RECIPE_LIST.value) {
                    val recipeListViewModel = hiltViewModel<RecipeListViewModel>()
                    RecipeListScreen(
                        onRecipeClick = { recipeId ->
                            navController.navigate("${NavScreenName.RECIPE_DETAILS}/$recipeId")
                        },
                        onFavoriteScreenButtonClick = {
                            navController.navigate(NavScreenName.FAVORITE_RECIPES.value)
                        },
                        recipeListViewModel = recipeListViewModel
                    )
                }
                composable(
                    route = "${NavScreenName.RECIPE_DETAILS.value}/{recipeId}",
                    arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val recipeId = backStackEntry.arguments?.getInt("recipeId")
                    val recipeDetailsViewModel = hiltViewModel<RecipeDetailsViewModel>()
                    RecipeDetailsScreen(
                        recipeId = recipeId!!,
                        onBackPressed = { navController.popBackStack() },
                        recipeDetailsViewModel = recipeDetailsViewModel
                    )
                }
                composable(route = NavScreenName.FAVORITE_RECIPES.value) {
                    val favoriteRecipesViewModel = hiltViewModel<FavoriteRecipesViewModel>()
                    FavoriteRecipeScreen(
                        onFavoriteRecipeClick = { recipeId ->
                            navController.navigate("${NavScreenName.RECIPE_DETAILS.value}/$recipeId")
                        },
                        favoriteRecipesViewModel = favoriteRecipesViewModel
                    )
                }
            }
        }
    }
}
