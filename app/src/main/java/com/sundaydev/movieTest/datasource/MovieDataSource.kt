package com.sundaydev.movieTest.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.sundaydev.movieTest.data.Movie
import com.sundaydev.movieTest.network.MovieService
import com.sundaydev.movieTest.ui.frament.MovieTabInfo
import com.sundaydev.movieTest.util.subscribeByCommon
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo


class MovieDataSource(
    private val apiService: MovieService,
    private val filterName: String,
    private val error: MutableLiveData<Boolean>,
    private val disposable: CompositeDisposable
) : PageKeyedDataSource<Int, Movie>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {
        apiCall(filterName, 1)
            .subscribeByCommon(onSuccess = { item ->
                callback.onResult(item.results, 0, item.total_results, null, item.page + 1); error.postValue(false)
            }, onError = { error.postValue(true) })
            .addTo(disposable)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        apiCall(filterName, params.key)
            .subscribeByCommon(onSuccess = { item ->
                callback.onResult(item.results, params.key + 1); error.postValue(false)
            }, onError = { error.postValue(true) })
            .addTo(disposable)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {}

    private fun apiCall(filter: String, page: Int) = when (filter) {
        MovieTabInfo.MOVIE_POPULAR.name -> apiService.getPopularMovie(page)
        MovieTabInfo.MOVIE_NOW_PLAYING.name -> apiService.getNowPlayingMovie(page)
        MovieTabInfo.MOVIE_UPCOMING.name -> apiService.getUpComingMovie(page)
        MovieTabInfo.MOVIE_TOP_RATE.name -> apiService.getTopRatedMovie(page)
        else -> apiService.getUpComingMovie(page)
    }

    fun refresh() = invalidate()
}

class MovieDataSourceFactory(
    private val apiService: MovieService,
    private val filterName: String,
    private val disposable: CompositeDisposable
) :
    DataSource.Factory<Int, Movie>() {
    val error = MutableLiveData<Boolean>()
    private lateinit var dataSource: MovieDataSource
    override fun create(): DataSource<Int, Movie> = MovieDataSource(apiService, filterName, error, disposable).also { dataSource = it }
    fun refresh() = dataSource.refresh()
}