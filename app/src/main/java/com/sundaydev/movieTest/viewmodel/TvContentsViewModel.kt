package com.sundaydev.movieTest.viewmodel

import com.sundaydev.movieTest.datasource.TvDataSourceFactory
import com.sundaydev.movieTest.network.MovieClient
import com.sundaydev.movieTest.repository.ContentsRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class TvContentsViewModel(filterName: String) : BaseViewModel(), KoinComponent {
    private val apiClient : MovieClient by inject()
    private  var tvDataSourceFactory = TvDataSourceFactory(apiClient.movieApi, filterName, disposable)
    private val repository: ContentsRepository by inject()
    val list = repository.loadTvs(filterName, tvDataSourceFactory, disposable)
}