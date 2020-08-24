package de.exercicse.jrossbach.podcaster.feature.search.data

import de.exercicse.jrossbach.podcaster.core.data.RetrofitProvider
import de.exercicse.jrossbach.podcaster.feature.channel.data.PodcastResponse
import io.reactivex.Single
import javax.inject.Inject

class PodcastApi @Inject constructor(private val retrofitProvider: RetrofitProvider) : PodcastRetrofitApi {

    private val api by lazy { retrofitProvider.createService(PodcastRetrofitApi::class.java) }

    override fun getPodCastSearchResponse(query: String): Single<PodCastSearchResponse> = api.getPodCastSearchResponse(query)

    override fun getPodcastMetaInfo(podcastId: String): Single<PodcastResponse> = api.getPodcastMetaInfo(podcastId)
}