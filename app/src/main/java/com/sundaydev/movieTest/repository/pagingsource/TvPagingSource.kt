package com.sundaydev.movieTest.repository.pagingsource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sundaydev.movieTest.data.Tv
import com.sundaydev.movieTest.network.MovieClient
import com.sundaydev.movieTest.repository.CONTENTS_PAGE_SIZE
import com.sundaydev.movieTest.ui.frament.TvTabInfo
import org.koin.core.KoinComponent
import org.koin.core.inject

class TvPagingSource(private val filterName: String) : PagingSource<Int, Tv>(), KoinComponent {
    private val apiService: MovieClient by inject()

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Tv> {
        return try {
            val page = params.key ?: 1
            val result = when (filterName) {
                TvTabInfo.TV_POPULAR.name -> apiService.movieApi.getPopularTv(page)
                TvTabInfo.TV_TODAY.name -> apiService.movieApi.getTodayTv(page)
                TvTabInfo.TV_NOW_PLAYING.name -> apiService.movieApi.getNowPlayingTv(page)
                TvTabInfo.TV_TOP_RATE.name -> apiService.movieApi.getTopRatedTv(page)
                else -> apiService.movieApi.getPopularTv(page)
            }
            LoadResult.Page(data = result.results, prevKey = result.page - 1, nextKey = result.page + 1)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getRefreshKey(state: PagingState<Int, Tv>): Int? {
        return state.anchorPosition?.let { index ->
            val prevPage = if (index - CONTENTS_PAGE_SIZE < 0) 0 else (index - CONTENTS_PAGE_SIZE)
            state.closestPageToPosition(prevPage)?.let { page -> if (prevPage == 0) null else page.nextKey }
        }
    }
}