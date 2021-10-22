package com.aldhykohar.mvvmnewsapp.ui

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.aldhykohar.mvvmnewsapp.R
import com.aldhykohar.mvvmnewsapp.base.BaseActivity
import com.aldhykohar.mvvmnewsapp.data.db.ArticleDatabase
import com.aldhykohar.mvvmnewsapp.data.repository.NewsRepository
import com.aldhykohar.mvvmnewsapp.databinding.ActivityNewsBinding

class NewsActivity : BaseActivity<ActivityNewsBinding>() {

    lateinit var viewModel: NewsViewModel

    override fun getViewBinding() = ActivityNewsBinding.inflate(layoutInflater)

    override fun initView() {
        val newsRepository = NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)

        with(binding) {
            bottomNavigationView.setupWithNavController(findNavController(R.id.newsNavHostFragment))
        }
    }

    override fun initObserver() {
    }
}