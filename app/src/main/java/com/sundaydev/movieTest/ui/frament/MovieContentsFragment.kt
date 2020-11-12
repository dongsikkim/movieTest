package com.sundaydev.movieTest.ui.frament

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.sundaydev.movieTest.BR
import com.sundaydev.movieTest.R
import com.sundaydev.movieTest.data.Movie
import com.sundaydev.movieTest.databinding.FragmentMovieContentsBinding
import com.sundaydev.movieTest.util.BindingViewHolder
import com.sundaydev.movieTest.viewmodel.MovieContentsViewModel
import kotlinx.android.synthetic.main.fragment_movie_contents.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

const val FILTER_NAME = "filterNAME"

fun createMovieContentsFragment(movieTabInfo: MovieTabInfo) = MovieContentsFragment().apply {
    arguments = bundleOf(FILTER_NAME to movieTabInfo)
}

class MovieContentsFragment : Fragment() {
    private val viewModelMovie: MovieContentsViewModel by viewModel { parametersOf(filterName) }
    private val movieAdapter: MovieContentsAdapter by lazy { MovieContentsAdapter(onClicks) }
    lateinit var filterName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tab = arguments?.getSerializable(FILTER_NAME) as MovieTabInfo
        filterName = tab.name
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        FragmentMovieContentsBinding.inflate(inflater).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = viewModelMovie
            contentsRecycler.adapter = movieAdapter
        }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refresh_layout.setOnRefreshListener { viewModelMovie.refresh() }
        retry.setOnClickListener { viewModelMovie.refresh() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModelMovie.list.observe(viewLifecycleOwner, Observer { setData(it) })
    }

    private fun setData(it: PagedList<Movie>?) {
        if (refresh_layout.isRefreshing) {
            movieAdapter.submitList(null)
        }
        movieAdapter.submitList(it)
        viewModelMovie.isRefresh.postValue(false)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.menu_refresh -> {
            refresh_layout.isRefreshing = true
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private val onClicks: ((Pair<AppCompatImageView, Movie>) -> Unit)? = { pair ->
        val extras = androidx.navigation.fragment.FragmentNavigatorExtras(pair.first to pair.first.transitionName)
        findNavController().navigate(R.id.detailFragment, bundleOf(KEY_MOVIE to pair.second.toDetail()), null, extras)
    }
}

class MovieContentsAdapter(private val onClicks: ((Pair<AppCompatImageView, Movie>) -> Unit)? = null) :
    PagedListAdapter<Movie, BindingViewHolder>(diffMovieUtil) {
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

val diffMovieUtil = object : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem == newItem
}