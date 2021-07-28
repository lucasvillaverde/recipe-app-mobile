package dev.lucasvillaverde.recipes.presenter.recipe_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dev.lucasvillaverde.recipes.databinding.ItemListRecipeBinding
import dev.lucasvillaverde.recipes.databinding.LoaderRecipeListItemBinding
import dev.lucasvillaverde.recipes.domain.model.RecipeModel
import jp.wasabeef.picasso.transformations.CropCircleTransformation


class RecipeAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var onItemClick: ((RecipeListItem.Recipe) -> Unit)? = null

    private val diffCallback =
        object : DiffUtil.ItemCallback<RecipeListItem>() {
            override fun areItemsTheSame(
                oldModel: RecipeListItem,
                newModel: RecipeListItem
            ): Boolean =
                oldModel.data?.id == newModel.data?.id

            override fun areContentsTheSame(
                oldModel: RecipeListItem,
                newModel: RecipeListItem
            ): Boolean =
                oldModel.hashCode() == newModel.hashCode()
        }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<RecipeListItem>) =
        differ.submitList(list)

    fun getCurrentList(): List<RecipeListItem> = differ.currentList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        LOADER_VIEW_HOLDER -> LoaderViewHolder(
            LoaderRecipeListItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )
        RECIPE_ITEM_VIEW_HOLDER -> RecipeViewHolder(
            ItemListRecipeBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )
        else -> throw NotImplementedError("ViewHolder not implemented.")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RecipeViewHolder -> {
                val recipe: RecipeListItem.Recipe =
                    differ.currentList[position] as RecipeListItem.Recipe
                holder.bind(recipe.data!!)
            }
            is LoaderViewHolder -> {

            }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun getItemViewType(position: Int): Int = when (differ.currentList[position]) {
        is RecipeListItem.Loader -> LOADER_VIEW_HOLDER
        is RecipeListItem.Recipe -> RECIPE_ITEM_VIEW_HOLDER
    }

    inner class RecipeViewHolder(private val binding: ItemListRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(
                    differ.currentList[bindingAdapterPosition] as RecipeListItem.Recipe
                )
            }
        }

        fun bind(recipeModel: RecipeModel) {
            binding.tvRecipeName.text = recipeModel.name
            binding.tvRecipeCategory.text = recipeModel.category
            Picasso.get().load(recipeModel.thumb).transform(CropCircleTransformation())
                .into(binding.ivRecipeLogo)
        }
    }

    inner class LoaderViewHolder(binding: LoaderRecipeListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        private const val LOADER_VIEW_HOLDER = 1
        private const val RECIPE_ITEM_VIEW_HOLDER = 2
    }
}