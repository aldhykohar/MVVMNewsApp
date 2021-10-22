package com.aldhykohar.mvvmnewsapp.network.api

import com.aldhykohar.mvvmnewsapp.network.model.news.NewsResponse
import com.aldhykohar.mvvmnewsapp.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Created by aldhykohar on 10/22/2021.
 */
interface NewsAPI {

    @GET("/v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode: String = "us",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>

    @GET("/v2/everything")
    suspend fun searchForNews(
        @Query("q")
        searchQuery: String,
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>
}