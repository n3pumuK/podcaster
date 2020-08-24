package de.exercicse.jrossbach.podcaster.feature.channel.ui.adapter

import android.view.ViewGroup
import de.exercicse.jrossbach.podcaster.base.ui.adapter.BaseAdapter
import de.exercicse.jrossbach.podcaster.feature.channel.data.PodcastEpisode

class PodcastEpisodesAdapter: BaseAdapter<PodcastEpisode, PodcastEpisodeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PodcastEpisodeViewHolder {
        return PodcastEpisodeViewHolder.create(parent)
    }
}