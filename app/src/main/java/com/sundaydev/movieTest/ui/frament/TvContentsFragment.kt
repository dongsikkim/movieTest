package com.sundaydev.movieTest.ui.frament

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.sundaydev.movieTest.databinding.FragmentTvContentsBinding
import com.sundaydev.movieTest.ui.adapters.TvContentsAdapter
import com.sundaydev.movieTest.viewmodel.TvContentsViewModel
import kotlinx.android.synthetic.main.fragment_tv_contents.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

fun createTvContentsFragment(tvInfo: TvTabInfo) = TvContentsFragment().apply {
    arguments = bundleOf(FILTER_NAME to tvInfo)
}

class TvContentsFragment : Fragment() {
    private val tvContentsViewModel: TvContentsViewModel by viewModel { parametersOf(filterName) }
    lateinit var filterName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tab = arguments?.getSerializable(FILTER_NAME) as TvTabInfo
        filterName = tab.name
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        FragmentTvContentsBinding.inflate(inflater).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = tvContentsViewModel
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(TvContentsAdapter()) {
            contents_recycler.adapter = this
            tvContentsViewModel.list.observe(viewLifecycleOwner, Observer(this::submitList))
        }
    }
}