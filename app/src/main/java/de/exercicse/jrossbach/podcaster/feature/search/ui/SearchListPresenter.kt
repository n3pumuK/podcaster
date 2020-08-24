package de.exercicse.jrossbach.podcaster.feature.search.ui

import de.exercicse.jrossbach.podcaster.feature.search.domain.PodcastDataSource
import de.exercicse.jrossbach.podcaster.base.ui.Presenter
import javax.inject.Inject

class SearchListPresenter @Inject constructor(
    private val dataSource: PodcastDataSource
) : Presenter<PodcastChannelView>() {

    fun search(searchString: String) {
        view.showProgress(true)
        subscribe(dataSource.loadSearch(searchString),
            onSuccess = { podcastItems: List<PodcastItemViewModel> ->
                view.showProgress(false)
                view.onItemsLoadedSuccessfully(podcastItems)
            },
            onError = { throwable: Throwable ->
                view.showProgress(false)
                view.showError(throwable.message)
                throwable.printStackTrace()
            }
        )
    }
}
