package de.exercicse.jrossbach.podcaster.feature.channel.data

import androidx.annotation.Keep

@Keep
data class PodcastEpisode(
    val id: String,
    val link: String,
    val audio: String,
    val image: String,
    val title: String,
    val thumbnail: String,
    val description: String,
    val pub_date_ms: Long,
    val listennotes_url: String,
    val audio_length_sec: Int,
    val listennotes_edit_url: String
)