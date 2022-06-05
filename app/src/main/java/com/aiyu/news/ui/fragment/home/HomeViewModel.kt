package com.aiyu.news.ui.fragment.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.aiyu.core.data.NewsDao
import com.aiyu.core.models.Article
import com.aiyu.core.repositories.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val dao: NewsDao
) : ViewModel() {
    val topHeading = newsRepository.getTopHeadingResult("in").cachedIn(viewModelScope)

    fun addArticle(article: Article) = viewModelScope.launch {
        dao.insert(article)
    }

    fun removeArticle(article: Article) = viewModelScope.launch {
        dao.delete(article)
    }

    val favArticle = dao.getAllArticles()
}