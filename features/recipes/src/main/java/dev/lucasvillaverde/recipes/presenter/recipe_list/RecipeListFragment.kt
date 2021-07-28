package dev.lucasvillaverde.recipes.presenter.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import dev.lucasvillaverde.common.base.presenter.BaseFragment
import dev.lucasvillaverde.common.base.presenter.NavDirection
import dev.lucasvillaverde.common.utils.DeviceUtils
import dev.lucasvillaverde.recipes.R
import dev.lucasvillaverde.recipes.databinding.FragmentRecipeListBinding
import dev.lucasvillaverde.recipes.presenter.recipe_list.adapter.RecipeAdapter
import dev.lucasvillaverde.recipes.presenter.recipe_list.adapter.RecipeListItem

@AndroidEntryPoint
class RecipeListFragment : BaseFragment() {
    lateinit var binding: FragmentRecipeListBinding

    private val recipeListViewModel: RecipeListViewModel by viewModels()
    private lateinit var recipeAdapter: RecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        actionBar?.hide()
        binding = FragmentRecipeListBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupMealRecyclerView()
        setupOnClickListeners()
        setupObserver()
        setupSearchView()
    }

    private fun setupMealRecyclerView() {
        recipeAdapter = RecipeAdapter()

        binding.recipeRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = recipeAdapter
            visibility = View.GONE
        }

        binding.recipeRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItem =
                    (binding.recipeRecyclerView.layoutManager as GridLayoutManager)
                        .findLastCompletelyVisibleItemPosition()

                val currentList = recipeAdapter.getCurrentList()

                if (lastVisibleItem == (currentList.size - 1)
                    && recipeListViewModel.pageState.value!!.isLoading.not()
                ) {
                    recipeAdapter.submitList(currentList.toMutableList().apply {
                        add(RecipeListItem.Loader)
                    })
                    recipeListViewModel.getNewRecipes(DEFAULT_RECIPE_FETCHING_COUNT)
                }
            }
        })

        recipeAdapter.onItemClick = {
            navigator.navigateToDirection(
                NavDirection.RecipeListToRecipeDetails(
                    bundleOf(
                        Pair(
                            NavDirection.RecipeListToRecipeDetails.recipeIdArgName,
                            it.data!!.id
                        )
                    )
                )
            )
        }
    }

    private fun setupSearchView() {
        binding.svRecipes.setOnClickListener {
            binding.svRecipes.isIconified = false
        }

        binding.svRecipes.setOnQueryTextFocusChangeListener { _, isFocused ->
            binding.svRecipes.isIconified = !isFocused
        }

        binding.svRecipes.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean = true

            override fun onQueryTextChange(text: String?): Boolean {
                recipeListViewModel.pageState.value?.data?.let { list ->
                    if (text.isNullOrEmpty()) recipeAdapter.submitList(list)
                    else recipeAdapter.submitList(list.filter {
                        it.data!!.name.contains(
                            text,
                            true
                        )
                    })
                }

                return true
            }
        })
    }

    private fun setupOnClickListeners() {
        binding.btnGetMeal.setOnClickListener {
            if (DeviceUtils.hasInternet(requireContext().applicationContext))
                recipeListViewModel.getNewRecipes(1)
            else
                Toast.makeText(
                    requireActivity(),
                    getString(R.string.no_internet_error),
                    Toast.LENGTH_LONG
                ).show()
        }

        binding.btnDeleteMeals.setOnClickListener {
            recipeListViewModel.deleteMeals()
        }

        binding.fabFavoriteRecipes.setOnClickListener {
            navigator
                .navigateToDirection(NavDirection.RecipeListToFavoriteRecipes())
        }
    }

    private fun setupObserver() {
        recipeListViewModel.pageState.observe(viewLifecycleOwner) {
            // setPageLoading(it.isLoading)

            it.data?.let { data ->
                populateRecyclerView(data)
                updateUI(data)
            }

            if (it.isError) {
                Toast.makeText(
                    requireActivity(),
                    getString(R.string.network_error),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun populateRecyclerView(recipeList: List<RecipeListItem.Recipe>) {
        recipeAdapter.submitList(recipeList)
    }

    private fun updateUI(listRecipe: List<RecipeListItem.Recipe>) {
        when {
            listRecipe.isNotEmpty() -> {
                binding.recipeRecyclerView.visibility = View.VISIBLE
                binding.imgEmptyState.visibility = View.GONE
                binding.tvEmptyState.text = getString(R.string.check_some_meal)
            }
            else -> {
                binding.recipeRecyclerView.visibility = View.GONE
                binding.imgEmptyState.visibility = View.VISIBLE
                binding.tvEmptyState.text = getString(R.string.hit_btn_get_some_meals)
            }
        }
    }

    private fun setPageLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.btnGetMeal.isClickable = false
            binding.btnDeleteMeals.isClickable = false
            binding.imgEmptyState.visibility = View.GONE
            binding.tvEmptyState.visibility = View.GONE
            binding.loader.visibility = View.VISIBLE
            binding.llMainList.alpha = 0.5F

            return
        }

        binding.loader.visibility = View.GONE
        binding.btnGetMeal.isClickable = true
        binding.btnDeleteMeals.isClickable = true
        binding.tvEmptyState.visibility = View.VISIBLE
        binding.llMainList.alpha = 1F
    }

    companion object {
        private const val DEFAULT_RECIPE_FETCHING_COUNT = 2
    }
}