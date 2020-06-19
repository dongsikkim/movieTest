package com.sundaydev.movieTest.di

import android.net.Uri
import com.google.gson.GsonBuilder
import com.sundaydev.movieTest.network.MovieClient
import com.sundaydev.movieTest.repository.*
import com.sundaydev.movieTest.util.UriAdapter
import com.sundaydev.movieTest.viewmodel.*
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val utilModule = module {
    single { GsonBuilder().registerTypeAdapter(Uri::class.java, UriAdapter()).setPrettyPrinting().create() }
}

val repositoryModule = module {
    single<ContentsRepository> { ContentsRepositoryImpl() }
    single<PeopleRepository> { PeopleRepositoryImpl() }
    single<MyInfoRepository> { MyInfoRepositoryImpl() }
    single { MovieClient() }
}

val viewModelModule = module {
    viewModel { SplashViewModel() }
    viewModel { MovieViewModel() }
    viewModel { PeopleDetailViewModel() }
    viewModel { PeopleViewModel() }
    viewModel { TvViewModel() }
    viewModel { (filterName: String) -> MovieContentsViewModel(filterName) }
    viewModel { (filterName: String) -> TvContentsViewModel(filterName) }
    viewModel { (movieId: Int) -> DetailViewModel(movieId) }
}

val applicationModules = listOf(
    utilModule,
    repositoryModule,
    viewModelModule
)