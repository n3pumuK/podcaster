package de.exercicse.jrossbach.podcast.search

import de.exercicse.jrossbach.podcast.network.ApiProvider
import io.reactivex.Single

class PodcastChannelDataSource {

    fun loadChannelItems(url: String): Single<List<PodcastItemViewModel>> {
        return ApiProvider.getChannelApi(url).podCastChannelResponse.map { response ->
            val podcastItemViewModelList = mutableListOf<PodcastItemViewModel>()
            response.channel.itemList?.let {
                for (item in it) {
                    val podcastItemViewModel = PodcastItemViewModel(
                        title = item.title,
                        url = item.enclosure.url,
                        imageUrl = response.channel.normalImage.normalImageUrl ?: response.channel.channelImage.itunesImageUrl,
                        category = item.category,
                        publishingDate = item.pubDate,
                        type = item.enclosure.type,
                        length = item.enclosure.length,
                        imageData = emptyMap()
                    )
                    podcastItemViewModelList.add(podcastItemViewModel)
                }
            }
            podcastItemViewModelList.toList()
        }
    }
}
