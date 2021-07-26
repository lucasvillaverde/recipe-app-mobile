package dev.lucasvillaverde.recipes.presenter.recipe_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dev.lucasvillaverde.recipes.databinding.ItemListRecipeBinding
import jp.wasabeef.picasso.transformations.CropCircleTransformation


class RecipeAdapter :
    RecyclerView.Adapter<RecipeAdapter.MealViewHolder>() {
    var onItemClick: ((dev.lucasvillaverde.recipes.domain.model.RecipeModel) -> Unit)? = null

    private val diffCallback =
        object : DiffUtil.ItemCallback<dev.lucasvillaverde.recipes.domain.model.RecipeModel>() {
            override fun areItemsTheSame(
                oldModel: dev.lucasvillaverde.recipes.domain.model.RecipeModel,
                newModel: dev.lucasvillaverde.recipes.domain.model.RecipeModel
            ): Boolean =
                oldModel.id == newModel.id

            override fun areContentsTheSame(
                oldModel: dev.lucasvillaverde.recipes.domain.model.RecipeModel,
                newModel: dev.lucasvillaverde.recipes.domain.model.RecipeModel
            ): Boolean =
                oldModel.hashCode() == newModel.hashCode()
        }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<dev.lucasvillaverde.recipes.domain.model.RecipeModel>) =
        differ.submitList(list)

    inner class MealViewHolder(private val binding: ItemListRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(differ.currentList[bindingAdapterPosition])
            }
        }

        fun bind(recipeEntity: dev.lucasvillaverde.recipes.domain.model.RecipeModel) {
            binding.tvRecipeName.text = recipeEntity.name
            binding.tvRecipeCategory.text = recipeEntity.category
            Picasso.get().load(recipeEntity.thumb).transform(CropCircleTransformation())
                .into(binding.ivRecipeLogo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder =
        MealViewHolder(
            ItemListRecipeBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val recipe: dev.lucasvillaverde.recipes.domain.model.RecipeModel =
            differ.currentList[position]
        holder.bind(recipe)
    }

    override fun getItemCount(): Int = differ.currentList.size
}