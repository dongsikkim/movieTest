package com.sundaydev.movieTest.repository.pagingsource

import androidx.paging.PagingSource
import com.sundaydev.movieTest.data.Movie
import org.koin.core.KoinComponent

class MoviePagingSource : PagingSource<Int, Movie>(), KoinComponent {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        TODO("Not yet implemented")
    }
}