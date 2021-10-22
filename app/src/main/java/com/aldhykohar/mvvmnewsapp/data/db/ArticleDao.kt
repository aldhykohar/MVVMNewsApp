package com.aldhykohar.mvvmnewsapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.aldhykohar.mvvmnewsapp.network.model.news.Articles


/**
 * Created by aldhykohar on 10/22/2021.
 */

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Articles): Long

    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<Articles>>

    @Delete
    suspend fun deleteArticles(article: Articles)
}