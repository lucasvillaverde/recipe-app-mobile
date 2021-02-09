package dev.lucasvillaverde.recipeapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dev.lucasvillaverde.recipeapp.data.local.entities.MealEntity
import dev.lucasvillaverde.recipeapp.databinding.MealListItemBinding
import jp.wasabeef.picasso.transformations.CropCircleTransformation


class MealAdapter(private var mealDataset: List<MealEntity>) :
    RecyclerView.Adapter<MealAdapter.MealViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<MealEntity>() {
        override fun areItemsTheSame(oldItem: MealEntity, newItem: MealEntity): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MealEntity, newItem: MealEntity): Boolean =
            oldItem.hashCode() == newItem.hashCode()
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<MealEntity>) = differ.submitList(list)

    var onItemClick: ((MealEntity) -> Unit)? = null

    inner class MealViewHolder(private val binding: MealListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(differ.currentList[bindingAdapterPosition])
            }
        }

        fun bind(mealEntity: MealEntity) {
            binding.mealTitle.text = mealEntity.name
            binding.mealDescription.text = mealEntity.category
            Picasso.get().load(mealEntity.thumb).transform(CropCircleTransformation())
                .into(binding.mealImg)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder =
        MealViewHolder(
            MealListItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal: MealEntity = differ.currentList[position]
        holder.bind(meal)
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun filter(queryText: String?) {
        if (queryText.isNullOrEmpty())
            return

        val filteredDataset = mealDataset.filter { it.name!!.contains(queryText, true) }
        submitList(filteredDataset)
    }
}