package com.sundaydev.movieTest.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

data class Tvs(val page: Int, val total_results: Int, val total_pages: Int, val results: List<Tv>)
data class Movies(val page: Int, val total_results: Int, val total_pages: Int, val results: List<Movie>)
data class Peoples(val page: Int, val total_results: Int, val total_pages: String, val results: List<People>)

data class Tv(
    val first_air_date: String, val origin_country: List<String>,
    val name: String, val original_name: String,
    val poster_path: String? = null,
    val popularity: Float = 0F,
    val original_language: String = "",
    var id: Int = -1,
    val media_type: String = "",
    val backdrop_path: String? = null,
    val vote_average: Float = 0F,
    val overview: String = "",
    val genre_ids: List<Int> = mutableListOf(),
    val vote_count: Int = 0
) {
    fun displayVote() = (vote_average * 10).toInt()
}

data class Movie(
    val adult: Boolean = false, val release_date: String = "",
    val original_title: String = "", val title: String = "",
    val video: Boolean = false, val poster_path: String? = null,
    val popularity: Float = 0F,
    val original_language: String = "",
    var id: Int = -1,
    val media_type: String = "",
    val backdrop_path: String? = null,
    val vote_average: Float = 0F,
    val overview: String = "",
    val genre_ids: List<Int> = mutableListOf(),
    val vote_count: Int = 0
) {
    fun displayVote() = (vote_average * 10).toInt()
    fun toDetail() = MovieDetail(
        adult = adult, id = id, release_date = release_date, original_title = original_title,
        title = title, video = video, poster_path = poster_path, popularity = popularity, original_language = original_language,
        backdrop_path = backdrop_path, vote_average = vote_average, overview = overview, vote_count = vote_count
    )
}

data class People(
    val profile_path: String, val adult: Boolean, val id: Int,
    /*val known_for: List<String>, */val name: String, val popularity: Float
) {
    fun toPeopleDetail() = PeopleDetail(id = id, adult = adult, name = name, popularity = popularity, profile_path = profile_path)
}

@Parcelize
data class PeopleDetail(
    val birthday: String = "", val known_for_department: String = "",
    val deathday: String = "", val id: Int = -1, val name: String = "", val also_known_as: List<String> = mutableListOf(),
    val gender: Int = 0, val biography: String = "", val popularity: Float = 0f, val place_of_birth: String? = null,
    val profile_path: String? = null, val adult: Boolean = false, val imdb_id: String = "",
    val homepage: String? = null
) : Parcelable

data class PeopleCredits(
    val cast: List<PeopleCast>, val crew: List<PeopleCrew>, val id: Int
)

data class PeopleCast(
    val id: Int, val original_language: String, val episode_count: Int,
    val overview: String, val origin_country: String, val original_name: String,
    val genre_ids: List<Int>, val name: String, val media_type: String,
    val poster_path: String?, val first_air_date: String, val vote_average: Int?,
    val vote_count: Int, val character: String, val backdrop_path: String?, val popularity: Float,
    val credit_id: String, val original_title: String, val video: Boolean,
    val release_date: Date, val title: String, val adult: Boolean
)

data class PeopleCrew(
    val id: Int,
    val department: String,
    val original_language: String,
    val episode_count: Int,
    val job: String,
    val overview: String,
    val origin_country: List<String>,
    val original_name: String,
    val vote_count: Int,
    val name: String,
    val media_type: String,
    val popularity: Float,
    val credit_id: String,
    val backdrop_path: String?,
    val first_air_date: String, val vote_average: Float, val genre_ids: List<Int>,
    val poster_path: String?, val original_title: String, val video: Boolean, val title: String,
    val adult: Boolean, val release_date: Date
)

@Parcelize
data class MovieDetail(
    val adult: Boolean = false, val backdrop_path: String? = null,
    val budget: Int = 0, val genres: List<Genres> = mutableListOf(),
    val homepage: String? = null, val id: Int = 0, val imdb_id: String? = null,
    val original_language: String = "", val original_title: String = "",
    val overview: String? = null, val popularity: Float = 0F,
    val poster_path: String? = null, val production_companies: List<Company> = mutableListOf(),
    val production_countries: List<Country> = mutableListOf(), val release_date: String = "",
    val revenue: Int = 0, val runtime: Int? = null, val spoken_languages: List<SpokenLanguage> = mutableListOf(),
    val status: String = "", val tagline: String? = null, val title: String = "", val video: Boolean = false,
    val vote_average: Float = 0F, val vote_count: Int = 0
) : Parcelable {
    fun displayVote() = (vote_average * 10).toInt()
}

data class Credit(val id: Int, val cast: List<Cast>, val crew: List<Crew>)

data class Cast(
    val cast_id: Int, val character: String, val credit_id: String,
    val gender: Int, val id: Int, val name: String, val order: Int,
    val profile_path: String?
)

data class Crew(
    val credit_id: String, val department: String,
    val gender: Int?, val id: Int, val job: String, val name: String,
    val profile_path: String?
)

@Parcelize
data class Genres(val id: Int, val name: String) : Parcelable

@Parcelize
data class Company(val name: String, val id: Int, val logo_path: String?, val origin_country: String) : Parcelable

@Parcelize
data class Country(val iso_3166_1: String, val name: String) : Parcelable

@Parcelize
data class SpokenLanguage(val iso_639_1: String, val name: String) : Parcelable