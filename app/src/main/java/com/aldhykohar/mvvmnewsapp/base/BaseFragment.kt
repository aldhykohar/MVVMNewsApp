package com.aldhykohar.mvvmnewsapp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding


/**
 * Created by aldhykohar on 10/22/2021.
 */

abstract class BaseFragment<VB : ViewBinding>() : Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getViewBinding()
        return binding.root
    }

    abstract fun getViewBinding(): VB

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
    }

    abstract fun initView()

    abstract fun initObserver()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}