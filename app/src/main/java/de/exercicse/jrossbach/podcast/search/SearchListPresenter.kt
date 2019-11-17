package de.exercicse.jrossbach.podcast.search

import de.exercicse.jrossbach.podcast.Presenter

class SearchListPresenter : Presenter<PodcastChannelView>() {

    private val channelDataSource: PodcastChannelDataSource = PodcastChannelDataSource()

    fun loadChannel(url: String) {
        view!!.showProgress(true)
        subscribe(channelDataSource.loadChannelItems(url),
            onSuccess = { podcastItems: List<PodcastItemViewModel> ->
                view!!.showProgress(false)
                view!!.onItemsLoadedSuccessfully(podcastItems)
            },
            onError = { throwable: Throwable ->
                view!!.showProgress(false)
                throwable.printStackTrace()
            }
        )
    }
}
