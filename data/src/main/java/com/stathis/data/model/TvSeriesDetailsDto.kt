package com.stathis.data.model

data class TvSeriesDetailsDto(
    val backdrop_path: String,
    val first_air_date: String,
    val genres: List<GenreDto>,
    val id: Int,
    val name: String,
    val number_of_episodes: Int,
    val number_of_seasons: Int,
    val overview: String,
    val poster_path: String,
    val vote_average: Double,
    val vote_count: Int
) {
    constructor() : this("", "", listOf(), 0, "", 0, 0, "", "", 0.0, 0)
}