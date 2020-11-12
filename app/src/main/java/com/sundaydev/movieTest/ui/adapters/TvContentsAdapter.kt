package com.sundaydev.movieTest.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import com.sundaydev.movieTest.BR
import com.sundaydev.movieTest.R
import com.sundaydev.movieTest.data.Tv
import com.sundaydev.movieTest.util.BindingViewHolder

class TvContentsAdapter : PagedListAdapter<Tv, BindingViewHolder>(Tv.diffTvUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder =
        BindingViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_tv_contents, parent, false))

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        holder.binding.setVariable(BR.item, getItem(position))
    }
}