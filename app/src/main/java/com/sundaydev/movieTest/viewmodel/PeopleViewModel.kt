package com.sundaydev.movieTest.viewmodel

import com.sundaydev.movieTest.repository.PeopleRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class PeopleViewModel : BaseViewModel(), KoinComponent {
    private val repository: PeopleRepository by inject()
    val list = repository.loadPeoples(disposable)
}