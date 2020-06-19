package com.sundaydev.movieTest.network

import android.net.TrafficStats
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

open class MovieClientBase : KoinComponent {
    private val gSon: Gson by inject()
    lateinit var movieApi: MovieService

    open fun updateEndPoint(host: String) {
        val retrofit = createClient(host)
        movieApi = retrofit.create(MovieService::class.java)
    }

    protected fun createClient(host: String): Retrofit {
        val okHttp3Client =
            OkHttpClient.Builder()
                .addInterceptor(CommonParam())
                .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
                .addInterceptor(TrafficStatInterceptor(Math.random().toInt()))
                .connectTimeout(DEFAULT_TIMEOUT_SECOND, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT_SECOND, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT_SECOND, TimeUnit.SECONDS).build()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gSon))
            .client(okHttp3Client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).baseUrl(host).build()
    }

    open class TrafficStatInterceptor internal constructor(private var trafficTag: Int) :
        Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            TrafficStats.setThreadStatsTag(trafficTag)
            return chain.proceed(chain.request())
        }
    }

    companion object {
        const val DEFAULT_TIMEOUT_SECOND = 3L
    }
}