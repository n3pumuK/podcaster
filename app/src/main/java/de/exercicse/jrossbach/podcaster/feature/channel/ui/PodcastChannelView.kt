package de.exercicse.jrossbach.podcaster.feature.channel.ui

import de.exercicse.jrossbach.podcaster.base.ui.BaseView
import de.exercicse.jrossbach.podcaster.feature.channel.data.PodcastResponse

interface PodcastChannelView: BaseView {
    fun onChannelLoaded(podcast: PodcastResponse)
}