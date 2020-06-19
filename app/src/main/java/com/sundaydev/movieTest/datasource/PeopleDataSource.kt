package com.sundaydev.movieTest.datasource

import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.sundaydev.movieTest.data.People
import com.sundaydev.movieTest.network.MovieService
import com.sundaydev.movieTest.util.subscribeByCommon
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

class PeopleDataSource(private val apiService: MovieService, private val disposable: CompositeDisposable) :
    PageKeyedDataSource<Int, People>() {
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, People>) {
        apiService.getPeoples(1)
            .subscribeByCommon(onSuccess = { item -> callback.onResult(item.results, 0, item.total_results, null, item.page + 1) })
            .addTo(disposable)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, People>) {
        apiService.getPeoples(params.key)
            .subscribeByCommon(onSuccess = { item -> callback.onResult(item.results, params.key + 1) })
            .addTo(disposable)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, People>) {}
}

class PeopleDataSourceFactory(
    private val apiService: MovieService,
    private val disposable: CompositeDisposable
) :
    DataSource.Factory<Int, People>() {
    override fun create(): DataSource<Int, People> = PeopleDataSource(apiService, disposable)
}
