package dev.lucasvillaverde.recipes.presenter.recipe_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dev.lucasvillaverde.recipes.R
import dev.lucasvillaverde.recipes.databinding.FragmentIngredientsBinding

class IngredientsFragment : Fragment() {
    private lateinit var binding: FragmentIngredientsBinding
    private var recipeId: Int? = null
    private val recipeDetailsViewModel: RecipeDetailsViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIngredientsBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { arguments ->
            recipeId = arguments.getInt(INGREDIENTS_FRAGMENT_RECIPE_ID)
            recipeDetailsViewModel.pageState
                .observe(viewLifecycleOwner, { it.data?.let { data -> updateUI(data) } })
        }
    }

    private fun updateUI(recipe: dev.lucasvillaverde.recipes.domain.model.RecipeModel) {
        val ingredientsListText =
            getIngredientList(recipe.ingredientsMeasures.keys.toTypedArray().asList())
        val ingredientsMeasureListText =
            getMeasuresList(recipe.ingredientsMeasures.values.toTypedArray().asList())
        binding.txtIngredients.text = ingredientsListText
        binding.txtMeasures.text = ingredientsMeasureListText
        binding.ingredientsRoot.visibility = View.VISIBLE
    }

    private fun getIngredientList(list: List<String?>) =
        list.filter { !it.isNullOrBlank() }.joinToString(prefix = "• ", separator = "\n\n• ")

    private fun getMeasuresList(list: List<String?>) =
        list.filter { !it.isNullOrBlank() }.joinToString(separator = "\n\n")

    companion object {
        private const val INGREDIENTS_FRAGMENT_RECIPE_ID = "ingredients_fragment_recipe_id"

        fun newInstance(recipeId: Int) = IngredientsFragment().apply {
            arguments = bundleOf(INGREDIENTS_FRAGMENT_RECIPE_ID to recipeId)
        }
    }
}