package de.exercicse.jrossbach.podcaster.feature.channel.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import de.exercicse.jrossbach.podcaster.R
import de.exercicse.jrossbach.podcaster.base.ui.adapter.BaseViewHolder
import de.exercicse.jrossbach.podcaster.feature.channel.data.PodcastEpisode

class PodcastEpisodeViewHolder(itemView: View) : BaseViewHolder<PodcastEpisode>(itemView) {

    private val thumbnailImage by lazy { itemView.findViewById<ImageView>(R.id.episode_thumbnail_image) }
    private val episodeTitle by lazy { itemView.findViewById<TextView>(R.id.episode_title_text_view) }

    override fun bind(item: PodcastEpisode) {
        episodeTitle.text = item.title
        Picasso.get()
            .load(item.thumbnail)
            .into(thumbnailImage)
    }

    companion object {

        fun create(viewGroup: ViewGroup): PodcastEpisodeViewHolder =
            PodcastEpisodeViewHolder(LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.podcast_episode_item_layout, viewGroup, false))
    }
}