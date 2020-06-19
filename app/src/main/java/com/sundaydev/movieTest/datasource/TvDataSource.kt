package com.sundaydev.movieTest.datasource

import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.sundaydev.movieTest.data.Tv
import com.sundaydev.movieTest.network.MovieService
import com.sundaydev.movieTest.ui.frament.TvTabInfo
import com.sundaydev.movieTest.util.subscribeByCommon
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

class TvDataSource(private val apiService: MovieService, private val filterName: String, private val disposable: CompositeDisposable) :
    PageKeyedDataSource<Int, Tv>() {
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Tv>) {
        when (filterName) {
            TvTabInfo.TV_POPULAR.name -> apiService.getPopularTv(1)
            TvTabInfo.TV_TODAY.name -> apiService.getTodayTv(1)
            TvTabInfo.TV_NOW_PLAYING.name -> apiService.getNowPlayingTv(1)
            TvTabInfo.TV_TOP_RATE.name -> apiService.getTopRatedTv(1)
            else -> apiService.getPopularTv(1)
        }.subscribeByCommon(onSuccess = { item -> callback.onResult(item.results, 0, item.total_results, null, item.page + 1) })
            .addTo(disposable)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Tv>) {
        when (filterName) {
            TvTabInfo.TV_POPULAR.name -> apiService.getPopularTv(params.key)
            TvTabInfo.TV_TODAY.name -> apiService.getTodayTv(params.key)
            TvTabInfo.TV_NOW_PLAYING.name -> apiService.getNowPlayingTv(params.key)
            TvTabInfo.TV_TOP_RATE.name -> apiService.getTopRatedTv(params.key)
            else -> apiService.getPopularTv(params.key)
        }.subscribeByCommon(onSuccess = { item -> callback.onResult(item.results, params.key + 1) }).addTo(disposable)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Tv>) {}

    fun refresh() = invalidate()
}

class TvDataSourceFactory(private val apiService: MovieService, private val filterName: String, val disposable: CompositeDisposable) :
    DataSource.Factory<Int, Tv>() {
    lateinit var dataSource: TvDataSource
    override fun create(): DataSource<Int, Tv> = TvDataSource(apiService, filterName, disposable).also { dataSource = it }
    fun refresh() = dataSource.refresh()
}