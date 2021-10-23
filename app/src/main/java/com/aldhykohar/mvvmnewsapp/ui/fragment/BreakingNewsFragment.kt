package com.aldhykohar.mvvmnewsapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aldhykohar.mvvmnewsapp.R
import com.aldhykohar.mvvmnewsapp.base.BaseFragment
import com.aldhykohar.mvvmnewsapp.databinding.FragmentBreakingNewsBinding
import com.aldhykohar.mvvmnewsapp.ui.NewsActivity
import com.aldhykohar.mvvmnewsapp.ui.NewsViewModel
import com.aldhykohar.mvvmnewsapp.ui.adapter.NewsAdapter
import com.aldhykohar.mvvmnewsapp.utils.Constants.Companion.QUERY_PAGE_SIZE
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
            addOnScrollListener(this@BreakingNewsFragment.scrollListener)
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
                        newsAdapter.differ.submitList(newsResponse.articles.toList())
                        val totalPages = newsResponse.totalResults / QUERY_PAGE_SIZE + 2
                        isLastPage = viewModel.breakingNewsPage == totalPages

                        if (isLastPage) {
                            binding.rvBreakingNews.setPadding(0, 0, 0, 0)
                        }
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

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate =
                isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning && isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                viewModel.getBreakingNews("us")
                isScrolling = false
            }
        }
    }

    private fun initProgressBar(state: Boolean) {
        binding.paginationProgressBar.visibility = if (state) View.VISIBLE else View.INVISIBLE
        isLoading = state
    }

    override fun getViewBinding() = FragmentBreakingNewsBinding.inflate(layoutInflater)
}