package com.aldhykohar.mvvmnewsapp.ui

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.aldhykohar.mvvmnewsapp.R
import com.aldhykohar.mvvmnewsapp.base.BaseActivity
import com.aldhykohar.mvvmnewsapp.data.db.ArticleDatabase
import com.aldhykohar.mvvmnewsapp.data.repository.NewsRepository
import com.aldhykohar.mvvmnewsapp.databinding.ActivityNewsBinding
import com.aldhykohar.mvvmnewsapp.ui.adapter.NewsAdapter

class NewsActivity : BaseActivity<ActivityNewsBinding>() {

    val viewModel by lazy {
        val newsRepository = NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(application, newsRepository)
        ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)
    }

    override fun getViewBinding() = ActivityNewsBinding.inflate(layoutInflater)

    override fun initView() {

        with(binding) {
            bottomNavigationView.setupWithNavController(findNavController(R.id.newsNavHostFragment))
        }
    }

    override fun initObserver() {
    }
}