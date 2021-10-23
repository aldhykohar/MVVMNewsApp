package com.aldhykohar.mvvmnewsapp.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aldhykohar.mvvmnewsapp.data.repository.NewsRepository


/**
 * Created by aldhykohar on 10/22/2021.
 */
class NewsViewModelProviderFactory(val app: Application, val newsRepository: NewsRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsViewModel(newsRepository, app) as T
    }
}