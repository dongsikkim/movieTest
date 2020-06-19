package com.sundaydev.movieTest.util

import android.util.Log.d
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.CheckReturnValue
import io.reactivex.annotations.SchedulerSupport
import io.reactivex.schedulers.Schedulers
import io.reactivex.rxkotlin.subscribeBy as rxKotlinSubscribeBy

private val onErrorStub: (Throwable) -> Unit = { d("sss", "${it.message}") }
private val onNextStub: (Any) -> Unit = {}
private val onCompleteStub: () -> Unit = {}

@CheckReturnValue
@SchedulerSupport(SchedulerSupport.NONE)
fun <T : Any> Single<T>.workOnSchedulerIo(): Single<T> =
    subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

@CheckReturnValue
@SchedulerSupport(SchedulerSupport.NONE)
fun <T : Any> Single<T>.subscribeByCommon(onError: (Throwable) -> Unit = onErrorStub, onSuccess: (T) -> Unit = onNextStub) =
    rxKotlinSubscribeBy(onError, onSuccess)