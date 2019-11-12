package de.exercicse.jrossbach.podcast.search

import de.exercicse.jrossbach.podcast.network.ApiProvider
import de.exercicse.jrossbach.podcast.network.PodCastSearchResponse
import io.reactivex.Single
import java.util.ArrayList

class PodcastDataSource {

    fun loadChannelItems(search: String): Single<List<PodcastItemViewModel>> {
        return ApiProvider().podcastApi.getPodCastSearchResponse(search).map { response: PodCastSearchResponse ->
            val podcastItemViewModelList = ArrayList<PodcastItemViewModel>()
            if (response.results != null) {
                for (item in response.results) {
                    val podcastItemViewModel = PodcastItemViewModel()
                    podcastItemViewModel.title = item.podcast_title_original
                    podcastItemViewModel.category = item.description_original
                    podcastItemViewModel.publishingDate = item.pub_date_ms
                    podcastItemViewModel.url = item.audio
                    podcastItemViewModel.length = item.audio_length_sec
                    podcastItemViewModel.imageUrl = item.image
                    podcastItemViewModelList.add(podcastItemViewModel)
                }
            }
            podcastItemViewModelList
        }
    }
}
