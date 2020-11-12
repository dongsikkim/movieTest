package com.sundaydev.movieTest.ui.frament

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.sundaydev.movieTest.BR
import com.sundaydev.movieTest.R
import com.sundaydev.movieTest.data.Tv
import com.sundaydev.movieTest.databinding.FragmentTvContentsBinding
import com.sundaydev.movieTest.util.BindingViewHolder
import com.sundaydev.movieTest.viewmodel.TvContentsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

fun createTvContentsFragment(tvInfo: TvTabInfo) = TvContentsFragment().apply {
    arguments = bundleOf(FILTER_NAME to tvInfo)
}

class TvContentsFragment : Fragment() {
    private val tvContentsViewModel: TvContentsViewModel by viewModel { parametersOf(filterName) }
    private val adapter: TvContentsAdapter by lazy { TvContentsAdapter() }
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
            contentsRecycler.adapter = adapter
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvContentsViewModel.list.observe(viewLifecycleOwner, Observer(adapter::submitList))
    }
}

class TvContentsAdapter : PagedListAdapter<Tv, BindingViewHolder>(diffTvUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder =
        BindingViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_tv_contents, parent, false))

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        holder.binding.setVariable(BR.item, getItem(position))
        holder.binding.executePendingBindings()
    }
}

val diffTvUtil = object : DiffUtil.ItemCallback<Tv>() {
    override fun areItemsTheSame(oldItem: Tv, newItem: Tv): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Tv, newItem: Tv): Boolean = oldItem == newItem
}