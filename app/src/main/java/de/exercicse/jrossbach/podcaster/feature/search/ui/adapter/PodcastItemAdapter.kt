package de.exercicse.jrossbach.podcaster.feature.search.ui.adapter

import android.view.ViewGroup
import de.exercicse.jrossbach.podcaster.base.ui.adapter.BaseAdapter
import de.exercicse.jrossbach.podcaster.feature.search.ui.PodcastItemViewModel

class PodcastItemAdapter : BaseAdapter<PodcastItemViewModel, PodcastItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PodcastItemViewHolder {
        return PodcastItemViewHolder.create(parent)
    }
}