package com.aldhykohar.mvvmnewsapp.data.repository

import com.aldhykohar.mvvmnewsapp.data.db.ArticleDatabase
import com.aldhykohar.mvvmnewsapp.network.api.RetrofitInstance


/**
 * Created by aldhykohar on 10/22/2021.
 */
class NewsRepository(val db: ArticleDatabase) {

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews()

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.searchForNews(searchQuery, pageNumber)
}