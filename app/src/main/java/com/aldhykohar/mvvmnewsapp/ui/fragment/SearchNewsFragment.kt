package com.aldhykohar.mvvmnewsapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.aldhykohar.mvvmnewsapp.R
import com.aldhykohar.mvvmnewsapp.base.BaseFragment
import com.aldhykohar.mvvmnewsapp.databinding.FragmentSearchNewsBinding
import com.aldhykohar.mvvmnewsapp.ui.NewsActivity
import com.aldhykohar.mvvmnewsapp.ui.NewsViewModel
import com.aldhykohar.mvvmnewsapp.ui.adapter.NewsAdapter
import com.aldhykohar.mvvmnewsapp.utils.Constants.Companion.SEARCH_NEWS_TIME_DELAY
import com.aldhykohar.mvvmnewsapp.utils.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 * Created by aldhykohar on 10/22/2021.
 */
class SearchNewsFragment :
    BaseFragment<FragmentSearchNewsBinding>() {

    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter

    override fun initView() {
        viewModel = (activity as NewsActivity).viewModel

        newsAdapter = NewsAdapter()
        with(binding) {
            rvSearchNews.apply {
                setHasFixedSize(true)
                adapter = newsAdapter
            }
        }
        initListener()
        initClick()
    }

    override fun initObserver() {
        viewModel.searchNews.observe(viewLifecycleOwner, {
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

    private fun initListener() {
        with(binding) {
            var job: Job? = null
            etSearch.addTextChangedListener { editable ->
                job?.cancel()
                job = MainScope().launch {
                    delay(SEARCH_NEWS_TIME_DELAY)
                    editable?.let {
                        if (editable.toString().isNotEmpty()) {
                            viewModel.searchNews(editable.toString())
                        }
                    }
                }
            }
        }
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

    private fun initProgressBar(state: Boolean) {
        binding.paginationProgressBar.visibility = if (state) View.VISIBLE else View.INVISIBLE
    }

    override fun getViewBinding() = FragmentSearchNewsBinding.inflate(layoutInflater)
}