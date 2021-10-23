package com.aldhykohar.mvvmnewsapp.ui.fragment

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.aldhykohar.mvvmnewsapp.R
import com.aldhykohar.mvvmnewsapp.base.BaseFragment
import com.aldhykohar.mvvmnewsapp.databinding.FragmentArticleBinding
import com.aldhykohar.mvvmnewsapp.ui.NewsActivity
import com.aldhykohar.mvvmnewsapp.ui.NewsViewModel
import com.google.android.material.snackbar.Snackbar


/**
 * Created by aldhykohar on 10/22/2021.
 */
class ArticlesFragment : BaseFragment<FragmentArticleBinding>() {

    lateinit var viewModel: NewsViewModel
    val args: ArticlesFragmentArgs by navArgs()


    override fun getViewBinding() = FragmentArticleBinding.inflate(layoutInflater)

    override fun initView() {
        viewModel = (activity as NewsActivity).viewModel
        val article = args.article
        with(binding) {
            webView.apply {
                webViewClient = WebViewClient()
                article.url?.let { loadUrl(it) }
            }

            fab.setOnClickListener {
                viewModel.saveArticle(article)
                Snackbar.make(view!!, "Article saved successfully", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun initObserver() {}
}