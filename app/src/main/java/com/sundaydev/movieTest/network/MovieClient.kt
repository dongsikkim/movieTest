package com.sundaydev.movieTest.network

const val URL_HOST = "https://api.themoviedb.org/3/"
const val URL_SUMMARY_IMAGE = "https://image.tmdb.org/t/p/w185/"
const val URL_ORIGIN_IMAGE = "https://image.tmdb.org/t/p/original/"
class MovieClient : MovieClientBase() {

    init {
        updateEndPoint(URL_HOST)
    }

    override fun updateEndPoint(host: String) {
        movieApi = createClient(host).create(MovieService::class.java)
    }
}