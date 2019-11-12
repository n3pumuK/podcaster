package de.exercicse.jrossbach.podcast.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiProvider {

    val podcastApi: PodcastApi = getRetrofit().create(PodcastApi::class.java)
    val gson = GsonBuilder().setLenient().create()

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .client(OkHttpClient.Builder().addInterceptor(AuthorizationIntercepter()).build())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    companion object {
        private val BASE_URL = "https://listen-api.listennotes.com/api/v2/"
    }
}
