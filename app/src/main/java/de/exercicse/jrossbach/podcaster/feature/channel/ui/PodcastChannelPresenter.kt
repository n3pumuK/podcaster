package de.exercicse.jrossbach.podcaster.feature.channel.ui

import de.exercicse.jrossbach.podcaster.base.ui.Presenter
import de.exercicse.jrossbach.podcaster.feature.search.domain.PodcastDataSource
import javax.inject.Inject

class PodcastChannelPresenter @Inject constructor(
    private val dataSource: PodcastDataSource
) : Presenter<PodcastChannelView>() {

    fun loadChannel(id: String) {
        view.showProgress(true)
        subscribe(dataSource.loadPodcast(id),
            onSuccess = { view.onChannelLoaded(it) },
            onError = { view.showError(it.localizedMessage) }
        )
    }
}