package dev.lucasvillaverde.favorite_recipes.presenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dev.lucasvillaverde.favorite_recipes.databinding.ItemListFavoriteRecipeBinding
import dev.lucasvillaverde.favorite_recipes.domain.model.FavoriteRecipe

class FavoriteRecipesAdapter(
    private inline val onRecipeItemClick: (recipeId: Int) -> Unit,
    private inline val onRemoveFromFavoriteClick: (recipeId: Int) -> Unit
) :
    RecyclerView.Adapter<FavoriteRecipesAdapter.FavoriteRecipeViewHolder>() {

    private val diffCallback = object :
        DiffUtil.ItemCallback<FavoriteRecipe>() {
        override fun areItemsTheSame(
            oldItem: FavoriteRecipe,
            newItem: FavoriteRecipe
        ): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: FavoriteRecipe,
            newItem: FavoriteRecipe
        ): Boolean =
            oldItem.hashCode() == newItem.hashCode()
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteRecipeViewHolder =
        FavoriteRecipeViewHolder(ItemListFavoriteRecipeBinding.inflate(LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(holder: FavoriteRecipeViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun submitList(list: List<FavoriteRecipe>) =
        differ.submitList(list)

    inner class FavoriteRecipeViewHolder(private val binding: ItemListFavoriteRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FavoriteRecipe) {
            binding.root.setOnClickListener {
                onRecipeItemClick(differ.currentList[bindingAdapterPosition].id)
            }
            binding.ivRemoveFromFavorite.setOnClickListener {
                onRemoveFromFavoriteClick(differ.currentList[bindingAdapterPosition].id)
            }
            binding.tvRecipeName.text = item.name
            binding.tvRecipeCategory.text = item.category
            Picasso.get().load(item.photoUrl).into(binding.ivRecipeLogo)
        }
    }
}