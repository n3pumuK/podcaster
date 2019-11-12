package de.exercicse.jrossbach.podcast.network

import androidx.annotation.Keep

@Keep
data class PodcastMetaData(
    val id: String,
    val rss: String,
    val audio: String,
    val image: String,
    val genre_ids: List<Int>,
    val itunes_id: String,
    val thumbnail: String,
    val podcast_id: String,
    val pub_date_ms: String,
    val title_original: String,
    val listennotes_url: String,
    val audio_length_sec: Int,
    val explicit_content: Boolean,
    val title_highlighted: String,
    val publisher_original: String,
    val description_original: String,
    val podcast_title_original: String,
    val description_highlighted: String,
    val podcast_listennotes_url: String,
    val transcripts_highlighted: List<String>,
    val podcast_title_highlighted: String
)