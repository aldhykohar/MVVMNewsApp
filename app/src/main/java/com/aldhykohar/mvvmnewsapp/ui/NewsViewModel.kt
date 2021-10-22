package com.aldhykohar.mvvmnewsapp.ui

import androidx.lifecycle.ViewModel
import com.aldhykohar.mvvmnewsapp.data.repository.NewsRepository


/**
 * Created by aldhykohar on 10/22/2021.
 */
class NewsViewModel(
    val newsRepository: NewsRepository
) : ViewModel() {
}