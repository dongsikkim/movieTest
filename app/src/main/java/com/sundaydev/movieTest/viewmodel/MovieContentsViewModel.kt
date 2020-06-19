package com.sundaydev.movieTest.viewmodel

import androidx.lifecycle.MutableLiveData
import com.sundaydev.movieTest.repository.ContentsRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class MovieContentsViewModel(filterName: String) : BaseViewModel(), KoinComponent {
    private val repository: ContentsRepository by inject()
    private val dataSource = repository.loadMovies(filterName, disposable)
    private val factory = dataSource.factory
    val isRefresh = MutableLiveData(false)
    val list = dataSource.movieData
    val error = dataSource.factory.error
    fun refresh() = repository.refreshMovie(factory).also { isRefresh.postValue(true) }
}