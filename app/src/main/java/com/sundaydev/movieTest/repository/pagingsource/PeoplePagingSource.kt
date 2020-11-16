package com.sundaydev.movieTest.repository.pagingsource

import androidx.paging.PagingSource
import com.sundaydev.movieTest.data.People
import org.koin.core.KoinComponent

class PeoplePagingSource : PagingSource<Int, People>(), KoinComponent {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, People> {
        TODO("Not yet implemented")
    }
}