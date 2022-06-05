package com.aiyu.news.ui.fragment.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.aiyu.core.data.NewsDao
import com.aiyu.core.models.Article
import com.aiyu.core.repositories.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: NewsRepository,
    private val dao: NewsDao
) : ViewModel() {
    val query = MutableStateFlow("corona")

    val searchResult = query.flatMapLatest {
        repository.getSearchResult(it).cachedIn(viewModelScope).asFlow()
    }

    fun addArticle(article: Article) = viewModelScope.launch {
        dao.insert(article)
    }

    fun removeArticle(article: Article) = viewModelScope.launch {
        dao.delete(article)
    }

    val favArticle = dao.getAllArticles()
}