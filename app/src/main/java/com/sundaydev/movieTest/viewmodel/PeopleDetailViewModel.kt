package com.sundaydev.movieTest.viewmodel

import androidx.lifecycle.MutableLiveData
import com.sundaydev.movieTest.data.PeopleDetail
import com.sundaydev.movieTest.repository.PeopleRepository
import com.sundaydev.movieTest.util.subscribeByCommon
import io.reactivex.rxkotlin.addTo
import org.koin.core.KoinComponent
import org.koin.core.inject

class PeopleDetailViewModel : BaseViewModel(), KoinComponent {
    private val repository: PeopleRepository by inject()
    val detailData = MutableLiveData<PeopleDetail>()

    fun loadPeopleDetail(peopleId: Int) {
        repository.getPeopleDetail(peopleId).subscribeByCommon { detailData.postValue(it) }.addTo(disposable)
    }
}