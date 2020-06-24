package dev.lucasvillaverde.recipeapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import dev.lucasvillaverde.recipeapp.R
import dev.lucasvillaverde.recipeapp.data.local.entities.MealEntity
import dev.lucasvillaverde.recipeapp.viewmodels.MealDetailsViewModel
import kotlinx.android.synthetic.main.fragment_ingredients.*

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val MEAL_ID = "MEAL_ID"

/**
 * A simple [Fragment] subclass.
 * Use the [IngredientsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class IngredientsFragment : Fragment() {
    private var mealId = 0
    private val mealDetailsViewModel: MealDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealId = it.getInt(MEAL_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ingredients, container, false)
        mealDetailsViewModel.getMeal(mealId).observe(viewLifecycleOwner, Observer { updateUI(it) })
        return view
    }

    private fun updateUI(meal: MealEntity?) {
        meal?.let {
            val ingredientsListText = getIngredientList(meal.getIngredients())
            val ingredientsMeasureListText = getMeasuresList(meal.getMeasures())
            txtIngredients.text = ingredientsListText
            txtMeasures.text = ingredientsMeasureListText
            ingredientsRoot.visibility = View.VISIBLE
        }

    }

    private fun getIngredientList(list: ArrayList<String?>) =
        list.filter { !it.isNullOrBlank() }.joinToString(prefix = "• ", separator = "\n\n• ")

    private fun getMeasuresList(list: ArrayList<String?>) =
        list.filter { !it.isNullOrBlank() }.joinToString(separator = "\n\n")

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param mealId Parameter 1.
         * @return A new instance of fragment IngredientsFragment.
         */
        @JvmStatic
        fun newInstance(mealId: Int) =
            IngredientsFragment().apply {
                arguments = Bundle().apply {
                    putInt(MEAL_ID, mealId)
                }
            }
    }
}