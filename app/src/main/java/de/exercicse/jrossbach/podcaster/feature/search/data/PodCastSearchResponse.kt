package de.exercicse.jrossbach.podcaster.feature.search.data

import androidx.annotation.Keep

@Keep
data class PodCastSearchResponse(
    val took: Float,
    val count: Int,
    val total: Int,
    val results: List<PodcastMetaData>,
    val next_offset: Int
)