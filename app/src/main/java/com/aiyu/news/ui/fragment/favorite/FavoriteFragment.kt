package com.aiyu.news.ui.fragment.favorite

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.annotation.AttrRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aiyu.core.models.Article
import com.aiyu.news.NavGraphDirections
import com.aiyu.news.R
import com.aiyu.news.databinding.FragmentFavBinding
import com.aiyu.news.ui.utils.openCustomChromeTab
import com.aiyu.news.ui.utils.openShareDeepLink
import com.google.android.material.color.MaterialColors
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.fragment_fav) {
    private val binding: FragmentFavBinding by viewBinding()
    private val viewModel: FavoriteViewModel by viewModels()
    private val articles: MutableList<Article> = arrayListOf()
    private val views: MutableList<CardView> = arrayListOf()
    private val articleLiveData: MutableLiveData<List<Article>> = MutableLiveData()
    private lateinit var favAdapter: FavAdapter
    private var actionMode: ActionMode? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favAdapter = FavAdapter({ article ->
            navigateToDescription(article)
        }, { title, url ->
            activity?.openShareDeepLink(title, url)
        }, {
            setUpActionBar()
        }, { it, view ->
            addValueInList(it, view)
        }, { article ->
            deleteFav(article)
        })
        binding.apply {
            showFav.apply {
                adapter = favAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
        viewModel.article.observe(viewLifecycleOwner) {
            favAdapter.submitList(it)
            binding.imageViewEmpty.isVisible = it.isEmpty()
        }
    }

    private fun deleteFav(article: Article) {
        viewModel.delete(article)
    }

    private fun addValueInList(article: Article, view: CardView) {
        views.add(view)
        if (articles.contains(article)) {
            changeCardColor(view, com.google.android.material.R.attr.colorSurface)
            articles.remove(article)
        } else {
            changeCardColor(view, R.attr.bottomBar)
            articles.add(article)
        }

        articleLiveData.postValue(articles)
    }

    private fun changeCardColor(view: CardView, @AttrRes res: Int) {
        view.setCardBackgroundColor(
            MaterialColors.getColor(
                requireContext(),
                res,
                Color.WHITE
            )
        )
    }

    private fun setUpActionBar() {
        val callback = object : ActionMode.Callback {
            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                mode?.menuInflater?.inflate(R.menu.menu_delete, menu)
                return true
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean =
                false

            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean =
                when (item?.itemId) {
                    R.id.menu_delete -> {
                        deleteArticles()
                        true
                    }
                    R.id.menu_delete_all -> {
                        findNavController().navigate(
                            NavGraphDirections.actionGlobalDeleteAllDialog()
                        )
                        true
                    }
                    else -> {
                        false
                    }
                }

            override fun onDestroyActionMode(mode: ActionMode?) {
                favAdapter.setIsMenuActive(false)
                resetListAndViews()
            }

        }
        actionMode = (activity as AppCompatActivity).startSupportActionMode(callback)
        articleLiveData.observe(viewLifecycleOwner) {
            actionMode?.title = "${it.size}"
        }
    }

    private fun resetListAndViews() {
        views.onEach {
            changeCardColor(it, android.viewbinding.library.R.attr.colorSurface)
        }
        views.clear()
        articles.clear()
        articleLiveData.postValue(articles)
    }

    private fun deleteArticles() {
        findNavController().navigate(
            NavGraphDirections.actionGlobalDeleteSelectedDialog(
                articles.toTypedArray()
            )
        )
        resetListAndViews()
    }

    private fun navigateToDescription(article: Article) {
        context?.openCustomChromeTab(article.url!!)
    }
}