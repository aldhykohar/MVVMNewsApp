package com.aldhykohar.mvvmnewsapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import com.aldhykohar.mvvmnewsapp.R
import com.aldhykohar.mvvmnewsapp.base.BaseFragment
import com.aldhykohar.mvvmnewsapp.databinding.FragmentBreakingNewsBinding
import com.aldhykohar.mvvmnewsapp.ui.NewsActivity
import com.aldhykohar.mvvmnewsapp.ui.NewsViewModel
import com.aldhykohar.mvvmnewsapp.ui.adapter.NewsAdapter
import com.aldhykohar.mvvmnewsapp.utils.Resource


/**
 * Created by aldhykohar on 10/22/2021.
 */
class BreakingNewsFragment :
    BaseFragment<FragmentBreakingNewsBinding>() {

    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter

    override fun initView() {
        viewModel = (activity as NewsActivity).viewModel

        newsAdapter = NewsAdapter()
        binding.rvBreakingNews.apply {
            setHasFixedSize(true)
            adapter = newsAdapter
        }

        initClick()
    }

    private fun initClick() {
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_breakingNewsFragment_to_articlesFragment, bundle
            )
        }
    }

    override fun initObserver() {
        viewModel.breakingNews.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    initProgressBar(false)
                    it.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)
                    }
                }
                is Resource.Error -> {
                    initProgressBar(false)
                    it.message?.let { message ->
                        Log.e("TAG", "an error occurred:$message ")
                    }
                }
                is Resource.Loading -> {
                    initProgressBar(true)
                }
            }
        })
    }

    private fun initProgressBar(state: Boolean) {
        binding.paginationProgressBar.visibility = if (state) View.VISIBLE else View.INVISIBLE
    }

    override fun getViewBinding() = FragmentBreakingNewsBinding.inflate(layoutInflater)
}