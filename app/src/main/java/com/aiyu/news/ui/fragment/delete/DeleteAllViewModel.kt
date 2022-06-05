package com.aiyu.news.ui.fragment.delete

import androidx.lifecycle.ViewModel
import com.aiyu.core.data.NewsDao
import com.aiyu.core.models.Article
import com.aiyu.core.utils.NewsScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteAllViewModel @Inject constructor(
    private val dao: NewsDao,
    @NewsScope private val scope: CoroutineScope
) : ViewModel() {
    fun delete(article: Article) = scope.launch {
        dao.delete(article)
    }

    fun deleteAll() = scope.launch {
        dao.deleteAll()
    }
}