package com.sundaydev.movieTest.repository

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.sundaydev.movieTest.data.MovieDetail
import com.sundaydev.movieTest.data.MovieResult
import com.sundaydev.movieTest.data.Tv
import com.sundaydev.movieTest.datasource.MovieDataSourceFactory
import com.sundaydev.movieTest.network.MovieClient
import com.sundaydev.movieTest.repository.pagingsource.TvPagingSource
import com.sundaydev.movieTest.util.workOnSchedulerIo
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent
import org.koin.core.inject

interface ContentsRepository {
    fun getMovieDetail(id: Int): Single<MovieDetail>
    fun getTvDetail(id: Int): Single<MovieDetail>

    fun loadTvs(filterName: String): LiveData<PagingData<Tv>>
    fun loadMovies(filterName: String, disposable: CompositeDisposable): MovieResult

    fun refreshTv(/*factory: TvDataSourceFactory?*/): Unit?
    fun refreshMovie(factory: MovieDataSourceFactory?): Unit?
}

const val CONTENTS_PAGE_SIZE = 30

class ContentsRepositoryImpl : ContentsRepository, KoinComponent {
    private val apiClient: MovieClient by inject()

    override fun getMovieDetail(id: Int): Single<MovieDetail> = apiClient.movieApi.getMovieDetail(id).workOnSchedulerIo()

    override fun getTvDetail(id: Int): Single<MovieDetail> = apiClient.movieApi.getTvDetail(id).workOnSchedulerIo()

    override fun loadMovies(filterName: String, disposable: CompositeDisposable) =
        MovieDataSourceFactory(apiClient.movieApi, filterName, disposable).let { factory ->
            MovieResult(factory, LivePagedListBuilder(factory, Config(pageSize = CONTENTS_PAGE_SIZE)).build())
        }

    override fun loadTvs(filterName: String) = Pager(PagingConfig(CONTENTS_PAGE_SIZE), pagingSourceFactory = { TvPagingSource(filterName) }).liveData

    override fun refreshTv() = Unit

    override fun refreshMovie(factory: MovieDataSourceFactory?) = factory?.refresh()
}