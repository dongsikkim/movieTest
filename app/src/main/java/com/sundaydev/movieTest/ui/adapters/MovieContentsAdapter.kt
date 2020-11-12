package com.sundaydev.movieTest.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import com.sundaydev.movieTest.BR
import com.sundaydev.movieTest.R
import com.sundaydev.movieTest.data.Movie
import com.sundaydev.movieTest.util.BindingViewHolder

class MovieContentsAdapter(private val onClicks: ((Pair<AppCompatImageView, Movie>) -> Unit)? = null) :
    PagedListAdapter<Movie, BindingViewHolder>(Movie.diffMovieUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder =
        BindingViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_movie_contents, parent, false))

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        holder.binding.setVariable(BR.item, getItem(position))
        holder.binding.executePendingBindings()
        holder.binding.root.setOnClickListener {
            val poster = holder.binding.root.findViewById<AppCompatImageView>(R.id.poster)
            getItem(position)?.let { onClicks?.invoke(Pair(poster, it)) }
        }
    }
}