package com.sundaydev.movieTest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.SerialDisposable
import org.koin.core.KoinComponent

enum class ViewStatus {
    INIT, LOADING, DONE, FAIL
}

open class BaseViewModel : ViewModel(), KoinComponent {
    val viewStatus = MutableLiveData(ViewStatus.INIT)
    val disposable: CompositeDisposable = CompositeDisposable()
    val serial: SerialDisposable = SerialDisposable()

    override fun onCleared() {
        disposable.dispose()
        serial.dispose()
        super.onCleared()
    }
}