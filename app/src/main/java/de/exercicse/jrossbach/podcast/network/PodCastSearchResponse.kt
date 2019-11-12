package de.exercicse.jrossbach.podcast.network

import androidx.annotation.Keep

@Keep
data class PodCastSearchResponse(
    val took: Float,
    val count: Int,
    val total: Int,
    val results: List<PodcastMetaData>,
    val next_offset: Int
)