package com.sundaydev.movieTest.ui.frament

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import com.sundaydev.movieTest.R
import com.sundaydev.movieTest.data.Movie
import com.sundaydev.movieTest.databinding.FragmentMovieContentsBinding
import com.sundaydev.movieTest.ui.adapters.MovieContentsAdapter
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
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(MovieContentsAdapter(onClicks)) {
            contents_recycler.adapter = this
            refresh_layout.setOnRefreshListener { viewModelMovie.refresh() }
            retry.setOnClickListener { viewModelMovie.refresh() }
            viewModelMovie.list.observe(viewLifecycleOwner, Observer { setData(it, this) })
        }
    }

    private fun setData(it: PagedList<Movie>?, movieAdapter: MovieContentsAdapter) {
        if (refresh_layout.isRefreshing) movieAdapter.submitList(null)
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