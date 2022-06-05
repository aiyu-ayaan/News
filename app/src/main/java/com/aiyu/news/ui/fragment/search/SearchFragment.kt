package com.aiyu.news.ui.fragment.search

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.aiyu.core.models.Article
import com.aiyu.news.R
import com.aiyu.news.databinding.FragmentSearchBinding
import com.aiyu.news.ui.fragment.home.HomeAdapter
import com.aiyu.news.ui.fragment.home.NewLoadStateAdapter
import com.aiyu.news.ui.utils.openCustomChromeTab
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {
    private val binding: FragmentSearchBinding by viewBinding()
    private val viewModel: SearchViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
        val homeAdapter = HomeAdapter { article ->
            navigateToDescription(article)
        }
        binding.apply {
            showSearch.apply {
                adapter = homeAdapter.withLoadStateHeaderAndFooter(
                    header = NewLoadStateAdapter { homeAdapter.retry() },
                    footer = NewLoadStateAdapter { homeAdapter.retry() }
                )
                layoutManager = LinearLayoutManager(requireContext())
            }
            editTextSearch.addTextChangedListener {
                it?.let {
                    viewModel.query.value = if (it.isEmpty()) "corona" else it.toString()
                    showSearch.isVisible = it.isNotBlank()
                }
            }
        }
        homeAdapter.loadStateFlow.asLiveData().observe(viewLifecycleOwner) { loadState ->
            binding.progressBarNews.isVisible = loadState.refresh is LoadState.Loading
            if (loadState.refresh is LoadState.Error) {
                Toast.makeText(
                    requireContext(),
                    "${(loadState.refresh as LoadState.Error).error}",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.searchResult.collect {
                homeAdapter.submitData(viewLifecycleOwner.lifecycle, it)
            }
        }
    }

    private fun navigateToDescription(article: Article) {
        context?.openCustomChromeTab(article.url!!)
    }
}