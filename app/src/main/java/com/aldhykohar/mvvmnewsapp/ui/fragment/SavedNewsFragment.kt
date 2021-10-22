package com.aldhykohar.mvvmnewsapp.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.aldhykohar.mvvmnewsapp.R
import com.aldhykohar.mvvmnewsapp.base.BaseFragment
import com.aldhykohar.mvvmnewsapp.databinding.FragmentSavedNewsBinding
import com.aldhykohar.mvvmnewsapp.ui.NewsActivity
import com.aldhykohar.mvvmnewsapp.ui.NewsViewModel
import com.aldhykohar.mvvmnewsapp.ui.adapter.NewsAdapter


/**
 * Created by aldhykohar on 10/22/2021.
 */
class SavedNewsFragment : BaseFragment<FragmentSavedNewsBinding>() {

    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    override fun getViewBinding() = FragmentSavedNewsBinding.inflate(layoutInflater)

    override fun initView() {
        viewModel = (activity as NewsActivity).viewModel

        with(binding) {
            rvSavedNews.apply {
                setHasFixedSize(true)
                adapter = newsAdapter
            }
        }

        initClick()
    }

    override fun initObserver() {

    }

    private fun initClick() {
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_searchNewsFragment_to_articlesFragment, bundle
            )
        }
    }

}