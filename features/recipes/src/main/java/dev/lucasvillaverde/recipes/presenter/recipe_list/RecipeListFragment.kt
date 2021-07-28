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
        setupSwipeRefreshLayout()
    }

    private fun setupSwipeRefreshLayout() {
        binding.srlRecipes.setOnRefreshListener {
            recipeListViewModel.fetchRecipeList()
        }
    }

    private fun setupMealRecyclerView() {
        recipeAdapter = RecipeAdapter()

        binding.recipeRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = recipeAdapter
            visibility = View.GONE
        }
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

        setupEndlessRecyclerView()
    }

    private fun setupEndlessRecyclerView() {
        binding.recipeRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItem =
                    (binding.recipeRecyclerView.layoutManager as GridLayoutManager)
                        .findLastCompletelyVisibleItemPosition()

                val currentList = recipeAdapter.getCurrentList()

                when {
                    currentList.isNullOrEmpty() -> return
                    currentList.isNotEmpty() -> {
                        if (lastVisibleItem == (currentList.size - 1)
                            && recipeListViewModel.pageState.value!!.isLoading.not()
                        ) {
                            recipeAdapter.submitList(currentList.toMutableList().apply {
                                add(RecipeListItem.Loader)
                            })
                            recipeListViewModel.getNewRecipes(DEFAULT_RECIPE_FETCHING_COUNT)
                        }
                    }
                }
            }
        })
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
        binding.fabDeleteRecipes.setOnClickListener {
            recipeListViewModel.deleteMeals()
        }

        binding.fabFavoriteRecipes.setOnClickListener {
            navigator
                .navigateToDirection(NavDirection.RecipeListToFavoriteRecipes())
        }
    }

    private fun setupObserver() {
        recipeListViewModel.pageState.observe(viewLifecycleOwner) {
            setPageLoading(it.isLoading, recipeAdapter.getCurrentList().isNullOrEmpty())

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
                binding.tvEmptyState.visibility = View.GONE
                binding.srlRecipes.isEnabled = false
            }
            else -> {
                binding.recipeRecyclerView.visibility = View.GONE
                binding.imgEmptyState.visibility = View.VISIBLE
                binding.tvEmptyState.visibility = View.VISIBLE
                binding.srlRecipes.isEnabled = true
            }
        }
    }

    private fun setPageLoading(isLoading: Boolean, isOnEmptyState: Boolean) {
        binding.fabDeleteRecipes.isClickable = !isLoading
        binding.fabFavoriteRecipes.isClickable = !isLoading
        binding.srlRecipes.isRefreshing = isOnEmptyState && isLoading

    }

    companion object {
        private const val DEFAULT_RECIPE_FETCHING_COUNT = 2
    }
}