package de.exercicse.jrossbach.podcaster.feature.search.data

import androidx.annotation.Keep

@Keep
data class Podcast(
    val id: String,
    val image: String,
    val genre_ids: List<Int>,
    val thumbnail: String,
    val title_original: String,
    val listennotes_url: String,
    val title_highlighted: String,
    val publisher_original: String,
    val publisher_highlighted: String
)