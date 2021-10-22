package com.aldhykohar.mvvmnewsapp.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.aldhykohar.mvvmnewsapp.R
import com.aldhykohar.mvvmnewsapp.ui.NewsActivity
import com.aldhykohar.mvvmnewsapp.ui.NewsViewModel


/**
 * Created by aldhykohar on 10/22/2021.
 */
class SavedNewsFragment : Fragment(R.layout.fragment_saved_news) {

    lateinit var viewModel: NewsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
    }
}