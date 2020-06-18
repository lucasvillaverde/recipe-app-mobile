package dev.lucasvillaverde.recipeapp.ui

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.BulletSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
class IngredientsFragment : Fragment() {
    private var mealId = 0
    private val mealDetailsViewModel: MealDetailsViewModel by lazy {
        val application = requireNotNull(activity?.application) {
            "Application must not be null!"
        }
        ViewModelProvider(this).get(MealDetailsViewModel::class.java)
    }

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

    private fun getIngredientList(list: ArrayList<String?>) = list.filter { !it.isNullOrBlank() }.joinToString(prefix = "• ", separator = "\n\n• ")
    private fun getMeasuresList(list: ArrayList<String?>) = list.filter { !it.isNullOrBlank() }.joinToString(separator = "\n\n")

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