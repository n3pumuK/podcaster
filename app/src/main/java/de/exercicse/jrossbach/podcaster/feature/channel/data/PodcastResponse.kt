package de.exercicse.jrossbach.podcaster.feature.channel.data

import androidx.annotation.Keep

@Keep
data class PodcastResponse(
    val id: String,
    val rss: String,
    val type: String,
    val email: String,
    val image: String,
    val title: String,
    val country: String,
    val website: String,
    val episodes: List<PodcastEpisode>,
    val listennotes_url: String,
    val total_episodes: Int,
    val latest_pub_date_ms: Long,
    val earliest_pub_date_ms: Long,
    val next_episode_pub_date: Long
)