package com.aiyu.core.models

import androidx.annotation.Keep
import okhttp3.internal.Util

@Keep
data class Article(
    val author: String?,
    val content: String?,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String?
)