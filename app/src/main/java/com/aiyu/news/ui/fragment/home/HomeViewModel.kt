package com.aiyu.news.ui.fragment.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private var breakingNewsPage = 2
    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()

    fun getTopHeading(countryCode: String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response = newsRepository.getTopHeading(countryCode, breakingNewsPage)
        breakingNews.postValue(handleTopHeadingResponse(response))
    }

    private fun handleTopHeadingResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }
}