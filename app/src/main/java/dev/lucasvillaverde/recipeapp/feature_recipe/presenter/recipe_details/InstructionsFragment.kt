package dev.lucasvillaverde.recipeapp.feature_recipe.presenter.recipe_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dev.lucasvillaverde.recipeapp.databinding.FragmentInstructionsBinding
import dev.lucasvillaverde.recipeapp.feature_recipe.domain.model.RecipeModel

class InstructionsFragment : Fragment() {
    private lateinit var binding: FragmentInstructionsBinding
    private var recipeId: Int? = null
    private val recipeDetailsViewModel: RecipeDetailsViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInstructionsBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { arguments ->
            recipeId = arguments.getInt(INSTRUCTIONS_FRAGMENT_RECIPE_ID)
            recipeDetailsViewModel.pageState.observe(viewLifecycleOwner, {
                it.data?.let { model ->
                    updateMealCard(model)
                }
            })
        }
    }

    private fun updateMealCard(recipe: RecipeModel) {
        binding.txtInstructions.text = recipe.instructions
        binding.instructionsRoot.visibility = View.VISIBLE
    }

    companion object {
        private const val INSTRUCTIONS_FRAGMENT_RECIPE_ID = "instructions_fragment_recipe_id"

        fun newInstance(recipeId: Int) = InstructionsFragment().apply {
            arguments = bundleOf(INSTRUCTIONS_FRAGMENT_RECIPE_ID to recipeId)
        }
    }
}