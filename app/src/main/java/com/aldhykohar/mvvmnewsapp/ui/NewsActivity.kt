package com.aldhykohar.mvvmnewsapp.ui

import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.aldhykohar.mvvmnewsapp.R
import com.aldhykohar.mvvmnewsapp.base.BaseActivity
import com.aldhykohar.mvvmnewsapp.databinding.ActivityNewsBinding

class NewsActivity : BaseActivity<ActivityNewsBinding>() {

    override fun getViewBinding() = ActivityNewsBinding.inflate(layoutInflater)

    override fun initView() {
        with(binding) {
            bottomNavigationView.setupWithNavController(findNavController(R.id.newsNavHostFragment))
        }
    }

    override fun initObserver() {
    }
}