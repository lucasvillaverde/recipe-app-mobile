package dev.lucasvillaverde.recipeapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.ArrayList

@Entity(tableName = "meals")
data class MealEntity(
    @PrimaryKey
    @SerializedName("idMeal")
    val id: Int,

    @SerializedName("strMeal")
    val name: String?,

    @SerializedName("strCategory")
    val category: String?,

    @SerializedName("strArea")
    val region: String?,

    @SerializedName("strInstructions")
    val instructions: String?,

    @SerializedName("strMealThumb")
    val thumb: String?,

    @SerializedName("strTags")
    val tags: String?,

    @SerializedName("strYoutube")
    val youtubeLink: String?,

    @SerializedName("strIngredient1")
    val ingredient1: String?,

    @SerializedName("strIngredient2")
    val ingredient2: String?,

    @SerializedName("strIngredient3")
    val ingredient3: String?,

    @SerializedName("strIngredient4")
    val ingredient4: String?,

    @SerializedName("strIngredient5")
    val ingredient5: String?,

    @SerializedName("strIngredient6")
    val ingredient6: String?,

    @SerializedName("strIngredient7")
    val ingredient7: String?,

    @SerializedName("strIngredient8")
    val ingredient8: String?,

    @SerializedName("strIngredient9")
    val ingredient9: String?,

    @SerializedName("strIngredient10")
    val ingredient10: String?,

    @SerializedName("strIngredient11")
    val ingredient11: String?,

    @SerializedName("strIngredient12")
    val ingredient12: String?,

    @SerializedName("strIngredient13")
    val ingredient13: String?,

    @SerializedName("strIngredient14")
    val ingredient14: String?,

    @SerializedName("strIngredient15")
    val ingredient15: String?,

    @SerializedName("strMeasure1")
    val measure1: String?,

    @SerializedName("strMeasure2")
    val measure2: String?,

    @SerializedName("strMeasure3")
    val measure3: String?,

    @SerializedName("strMeasure4")
    val measure4: String?,

    @SerializedName("strMeasure5")
    val measure5: String?,

    @SerializedName("strMeasure6")
    val measure6: String?,

    @SerializedName("strMeasure7")
    val measure7: String?,

    @SerializedName("strMeasure8")
    val measure8: String?,

    @SerializedName("strMeasure9")
    val measure9: String?,

    @SerializedName("strMeasure10")
    val measure10: String?,

    @SerializedName("strMeasure11")
    val measure11: String?,

    @SerializedName("strMeasure12")
    val measure12: String?,

    @SerializedName("strMeasure13")
    val measure13: String?,

    @SerializedName("strMeasure14")
    val measure14: String?,

    @SerializedName("strMeasure15")
    val measure15: String?

) {

    fun getIngredients(): ArrayList<String?> {
        val ingredients = ArrayList<String?>()
        ingredients.add(ingredient1)
        ingredients.add(ingredient2)
        ingredients.add(ingredient3)
        ingredients.add(ingredient4)
        ingredients.add(ingredient5)
        ingredients.add(ingredient6)
        ingredients.add(ingredient7)
        ingredients.add(ingredient8)
        ingredients.add(ingredient9)
        ingredients.add(ingredient10)
        ingredients.add(ingredient11)
        ingredients.add(ingredient12)
        ingredients.add(ingredient13)
        ingredients.add(ingredient14)
        ingredients.add(ingredient15)
        return ingredients
    }

    fun getMeasures(): MutableList<String?>? {
        val ingredients: MutableList<String?> = ArrayList()
        ingredients.add(measure1)
        ingredients.add(measure2)
        ingredients.add(measure3)
        ingredients.add(measure4)
        ingredients.add(measure5)
        ingredients.add(measure6)
        ingredients.add(measure7)
        ingredients.add(measure8)
        ingredients.add(measure9)
        ingredients.add(measure10)
        ingredients.add(measure11)
        ingredients.add(measure12)
        ingredients.add(measure13)
        ingredients.add(measure14)
        ingredients.add(measure15)
        return ingredients
    }

    fun getYoutubeVideoID(): String? {
        return youtubeLink?.let {
            it.substring(it.indexOf('=') + 1, it.length)
        }
    }
}