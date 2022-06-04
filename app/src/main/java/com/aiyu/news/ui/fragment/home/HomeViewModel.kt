package com.aiyu.news.ui.fragment.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.aiyu.core.models.NewsResponse
import com.aiyu.core.repositories.NewsRepository
import com.aiyu.core.utils.Resource
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {
    val topHeading = newsRepository.getTopHeadingResult("in").cachedIn(viewModelScope)
}