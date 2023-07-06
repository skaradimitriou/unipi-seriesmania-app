package com.stathis.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TvSeriesDetails(
    val backdropPath: String,
    val firstAirDate: String,
    val genres: String,
    val id: Int,
    val name: String,
    val numberOfEpisodes: Int,
    val numberOfSeasons: Int,
    val overview: String,
    val posterPath: String,
    val voteAverage: Double,
    val voteCount: Int
) : UiModel, Parcelable {
    constructor() : this("", "", "", 0, "", 0, 0, "", "", 0.0, 0)

    override fun equalsContent(obj: UiModel): Boolean = when (obj) {
        is TvSeriesDetails -> id == obj.id && name == obj.name
        else -> false
    }
}