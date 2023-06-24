package com.stathis.domain.model


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
) : UiModel {
    override fun equalsContent(obj: UiModel): Boolean = when (obj) {
        is TvSeriesDetails -> id == obj.id && name == obj.name
        else -> false
    }
}