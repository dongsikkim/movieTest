package com.sundaydev.movieTest.viewmodel

import androidx.lifecycle.MutableLiveData
import com.sundaydev.movieTest.data.MovieDetail
import com.sundaydev.movieTest.repository.ContentsRepository
import com.sundaydev.movieTest.util.subscribeByCommon
import io.reactivex.rxkotlin.addTo
import org.koin.core.KoinComponent
import org.koin.core.inject

class DetailViewModel(movieId: Int) : BaseViewModel(), KoinComponent {
    private val repository: ContentsRepository by inject()
    val detailData = MutableLiveData<MovieDetail>()

    init {
        repository.getMovieDetail(movieId).subscribeByCommon(onSuccess = {detailData.postValue(it)}).addTo(disposable)
    }
}