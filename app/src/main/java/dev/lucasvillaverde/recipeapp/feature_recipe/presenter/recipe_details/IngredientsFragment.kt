package dev.lucasvillaverde.recipeapp.feature_recipe.presenter.recipe_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.lucasvillaverde.recipeapp.R
import dev.lucasvillaverde.recipeapp.feature_recipe.data.local.entities.MealEntity
import dev.lucasvillaverde.recipeapp.databinding.FragmentIngredientsBinding

@AndroidEntryPoint
class IngredientsFragment : Fragment(R.layout.fragment_ingredients) {
    private lateinit var binding: FragmentIngredientsBinding
    private val mealDetailsViewModel: MealDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIngredientsBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.intent?.let { intent ->
            val mealId = intent.getIntExtra("MEAL_ID", 0)
            mealDetailsViewModel.getMeal(mealId).observe(viewLifecycleOwner, { updateUI(it) })
        }
    }

    private fun updateUI(meal: MealEntity?) {
        meal?.let {
            val ingredientsListText = getIngredientList(meal.getIngredients())
            val ingredientsMeasureListText = getMeasuresList(meal.getMeasures())
            binding.txtIngredients.text = ingredientsListText
            binding.txtMeasures.text = ingredientsMeasureListText
            binding.ingredientsRoot.visibility = View.VISIBLE
        }

    }

    private fun getIngredientList(list: ArrayList<String?>) =
        list.filter { !it.isNullOrBlank() }.joinToString(prefix = "• ", separator = "\n\n• ")

    private fun getMeasuresList(list: ArrayList<String?>) =
        list.filter { !it.isNullOrBlank() }.joinToString(separator = "\n\n")
}