package com.aiyu.core.repositories

import com.aiyu.core.api.NewsApi
import javax.inject.Inject


class NewsRepository @Inject constructor(
    private val api: NewsApi
) {

    suspend fun getTopHeading(countryCode: String, pageNumber: Int) =
        api.getHeadLines(countryCode, pageNumber)
}