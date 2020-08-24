package de.exercicse.jrossbach.podcaster.feature.search.data

import androidx.annotation.Keep

@Keep
data class PodcastMetaData(
    val id: String,
    val rss: String,
    val audio: String,
    val image: String,
    val podcast: Podcast,
    val itunes_id: String,
    val thumbnail: String,
    val pub_date_ms: String,
    val title_original: String,
    val listennotes_url: String,
    val audio_length_sec: Int,
    val explicit_content: Boolean,
    val title_highlighted: String,
    val description_original: String,
    val description_highlighted: String,
    val transcripts_highlighted: List<String>
)