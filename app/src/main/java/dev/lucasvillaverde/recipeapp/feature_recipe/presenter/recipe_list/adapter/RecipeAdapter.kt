package dev.lucasvillaverde.recipeapp.feature_recipe.presenter.recipe_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dev.lucasvillaverde.recipeapp.databinding.MealListItemBinding
import dev.lucasvillaverde.recipeapp.feature_recipe.data.local.model.RecipeEntity
import jp.wasabeef.picasso.transformations.CropCircleTransformation


class RecipeAdapter :
    RecyclerView.Adapter<RecipeAdapter.MealViewHolder>() {
    var onItemClick: ((RecipeEntity) -> Unit)? = null

    private val diffCallback = object : DiffUtil.ItemCallback<RecipeEntity>() {
        override fun areItemsTheSame(oldItem: RecipeEntity, newItem: RecipeEntity): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: RecipeEntity, newItem: RecipeEntity): Boolean =
            oldItem.hashCode() == newItem.hashCode()
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<RecipeEntity>) = differ.submitList(list)

    inner class MealViewHolder(private val binding: MealListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(differ.currentList[bindingAdapterPosition])
            }
        }

        fun bind(recipeEntity: RecipeEntity) {
            binding.mealTitle.text = recipeEntity.name
            binding.mealDescription.text = recipeEntity.category
            Picasso.get().load(recipeEntity.thumb).transform(CropCircleTransformation())
                .into(binding.mealImg)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder =
        MealViewHolder(
            MealListItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val recipe: RecipeEntity = differ.currentList[position]
        holder.bind(recipe)
    }

    override fun getItemCount(): Int = differ.currentList.size
}