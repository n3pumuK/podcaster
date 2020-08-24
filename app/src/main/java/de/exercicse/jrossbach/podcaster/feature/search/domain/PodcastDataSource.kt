package de.exercicse.jrossbach.podcaster.feature.search.domain

import android.net.Uri
import de.exercicse.jrossbach.podcaster.feature.channel.data.PodcastResponse
import de.exercicse.jrossbach.podcaster.feature.search.ui.PodcastItemViewModel
import de.exercicse.jrossbach.podcaster.feature.search.data.PodCastSearchResponse
import de.exercicse.jrossbach.podcaster.feature.search.data.PodcastApi
import io.reactivex.Single
import javax.inject.Inject

class PodcastDataSource @Inject constructor(private val podcastApi: PodcastApi) {

    fun loadSearch(search: String): Single<List<PodcastItemViewModel>> {
        return podcastApi.getPodCastSearchResponse(Uri.encode(search)).map { response: PodCastSearchResponse ->
            response.results.map {
                PodcastItemViewModel(
                    id = it.podcast.id,
                    title = it.podcast.title_original,
                    category = it.description_original,
                    publishingDate = it.pub_date_ms,
                    url = it.audio,
                    length = it.audio_length_sec,
                    imageUrl = it.image)
            }
        }
    }

    fun loadPodcast(id: String): Single<PodcastResponse> {
        return podcastApi.getPodcastMetaInfo(id)
    }
}
