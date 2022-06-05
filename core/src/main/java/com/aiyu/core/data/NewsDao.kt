package com.aiyu.core.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.aiyu.core.models.Article

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article)

    @Query("SELECT * FROM article")
    fun getAllArticles(): LiveData<List<Article>>

    @Delete()
    suspend fun delete(article: Article)

    @Query("DELETE FROM article")
    suspend fun deleteAll()
}