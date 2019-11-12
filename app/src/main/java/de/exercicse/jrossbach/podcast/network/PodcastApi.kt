package de.exercicse.jrossbach.podcast.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface PodcastApi {

    @GET("search")
    fun getPodCastSearchResponse(@Query("q") query: String): Single<PodCastSearchResponse>
}
