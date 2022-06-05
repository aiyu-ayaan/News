package com.aiyu.news.ui.fragment.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aiyu.core.data.NewsDao
import com.aiyu.core.models.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val dao: NewsDao
) : ViewModel() {

    fun delete(article: Article) = viewModelScope.launch {
        dao.delete(article)
    }

    val article = dao.getAllArticles()
}