package de.exercicse.jrossbach.podcast.search

import de.exercicse.jrossbach.podcast.network.ApiProvider
import io.reactivex.Single
import java.util.ArrayList

class PodcastChannelDataSource {

    fun loadChannelItems(url: String): Single<List<PodcastItemViewModel>> {
        return ApiProvider.getChannelApi(url).podCastChannelResponse.map { response ->
            val podcastItemViewModelList = ArrayList<PodcastItemViewModel>()
            val itemList = response.channel.itemList
            if (itemList != null) {
                for (item in itemList) {
                    val podcastItemViewModel = PodcastItemViewModel()
                    podcastItemViewModel.title = item.title
                    podcastItemViewModel.category = item.category
                    podcastItemViewModel.publishingDate = item.pubDate
                    podcastItemViewModel.url = item.enclosure.url
                    podcastItemViewModel.type = item.enclosure.type
                    podcastItemViewModel.length = item.enclosure.length
                    val imageUrl = if (response.channel.normalImage.normalImageUrl != null) response.channel.normalImage.normalImageUrl else response.channel.channelImage.itunesImageUrl
                    podcastItemViewModel.imageUrl = imageUrl
                    podcastItemViewModelList.add(podcastItemViewModel)
                }
            }
            podcastItemViewModelList
        }
    }
}
