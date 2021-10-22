package com.aldhykohar.mvvmnewsapp.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.aldhykohar.mvvmnewsapp.R
import com.aldhykohar.mvvmnewsapp.base.BaseFragment
import com.aldhykohar.mvvmnewsapp.databinding.FragmentSavedNewsBinding
import com.aldhykohar.mvvmnewsapp.ui.NewsActivity
import com.aldhykohar.mvvmnewsapp.ui.NewsViewModel
import com.aldhykohar.mvvmnewsapp.ui.adapter.NewsAdapter
import com.google.android.material.snackbar.Snackbar


/**
 * Created by aldhykohar on 10/22/2021.
 */
class SavedNewsFragment : BaseFragment<FragmentSavedNewsBinding>() {

    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    override fun getViewBinding() = FragmentSavedNewsBinding.inflate(layoutInflater)

    override fun initView() {
        viewModel = (activity as NewsActivity).viewModel
        newsAdapter = NewsAdapter()

        with(binding) {
            rvSavedNews.apply {
                setHasFixedSize(true)
                adapter = newsAdapter
            }
        }

        initClick()
        initListener()
    }

    override fun initObserver() {
        viewModel.getSavedNews().observe(viewLifecycleOwner, { articles ->
            newsAdapter.differ.submitList(articles)
        })
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

    private fun initListener() {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.differ.currentList[position]
                viewModel.deleteArticle(article)
                Snackbar.make(view!!, "Successfully delete article", Snackbar.LENGTH_SHORT).apply {
                    setAction("Undo") {
                        viewModel.saveArticle(article)
                    }
                    show()
                }
            }

        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvSavedNews)
        }
    }

}