package com.aiyu.news.ui.fragment.search

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.aiyu.news.R
import com.aiyu.news.databinding.FragmentSearchBinding
import com.aiyu.news.ui.fragment.home.HomeAdapter
import com.aiyu.news.ui.fragment.home.NewLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {
    private val binding: FragmentSearchBinding by viewBinding()
    private val viewModel: SearchViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val homeAdapter = HomeAdapter()
        binding.apply {
            showSearch.apply {
                adapter = homeAdapter.withLoadStateHeaderAndFooter(
                    header = NewLoadStateAdapter { homeAdapter.retry() },
                    footer = NewLoadStateAdapter { homeAdapter.retry() }
                )
                layoutManager = LinearLayoutManager(requireContext())
            }
            editTextSearch.addTextChangedListener {
                viewModel.query.value = it.toString()
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.searchResult.collect {
                homeAdapter.submitData(viewLifecycleOwner.lifecycle, it)
            }
        }
    }
}