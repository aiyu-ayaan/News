package com.aiyu.core.api

import android.os.Build
import com.aiyu.core.BuildConfig
import com.aiyu.core.models.NewsResponse
import com.aiyu.core.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("v2/top-headlines")
    suspend fun getHeadLines(
        @Query("country") countryCode: String = "in",
        @Query("page") pageNumber: Int = 1,
        @Query("pageSize") pageSize: Int = 20,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): NewsResponse

    @GET("v2/everything")
    suspend fun getSearchForNews(
        @Query("q") query: String,
        @Query("page") pageNumber: Int = 1,
        @Query("pageSize") pageSize: Int = 20,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): NewsResponse
}