package com.sundaydev.movieTest.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.sundaydev.movieTest.repository.ContentsRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class TvContentsViewModel(filterName: String) : BaseViewModel(), KoinComponent {
    private val repository: ContentsRepository by inject()
    val list = repository.loadTvs(filterName).cachedIn(viewModelScope)
}