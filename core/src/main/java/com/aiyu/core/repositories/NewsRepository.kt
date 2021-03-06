package com.aiyu.core.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.aiyu.core.api.NewsApi
import javax.inject.Inject


class NewsRepository @Inject constructor(
    private val api: NewsApi
) {

    fun getTopHeadingResult(countryCode: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { NewsTopHeadingPagingSource(countryCode, api) }
        ).liveData

    fun getSearchResult(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { NewsSearchPagingSource(query, api) }
        ).liveData
}