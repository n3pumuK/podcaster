package de.exercicse.jrossbach.podcaster.core.data

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class RetrofitProvider @Inject constructor(okHttpClient: OkHttpClient) {

    private val retrofit: Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

    fun <T> createService(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    companion object {
        private const val BASE_URL = "https://listen-api.listennotes.com/api/v2/"
    }
}
