package de.exercicse.jrossbach.podcaster.feature.search.data

import de.exercicse.jrossbach.podcaster.feature.channel.data.PodcastResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PodcastRetrofitApi {

    @GET("search")
    fun getPodCastSearchResponse(@Query("q") query: String): Single<PodCastSearchResponse>

    @GET("/podcasts/{id}")
    fun getPodcastMetaInfo(@Path("id") podcastId: String): Single<PodcastResponse>
}
