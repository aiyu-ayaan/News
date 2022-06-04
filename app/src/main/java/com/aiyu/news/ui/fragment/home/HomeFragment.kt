package com.aiyu.news.ui.fragment.home

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.aiyu.core.utils.Resource
import com.aiyu.news.R
import com.aiyu.news.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding: FragmentHomeBinding by viewBinding()
    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val homeAdapter = HomeAdapter()
        binding.apply {
            showHeadings.apply {
                adapter = homeAdapter.withLoadStateHeaderAndFooter(
                    header = NewLoadStateAdapter{homeAdapter.retry()},
                    footer = NewLoadStateAdapter{homeAdapter.retry()}
                )
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.topHeading.observe(viewLifecycleOwner) {
                homeAdapter.submitData(viewLifecycleOwner.lifecycle, it)
            }
        }
    }
}