package com.stathis.data.model

data class TvSeriesDto(
    val original_name: String? = null,
    val genre_ids: List<Int>? = null,
    val name: String? = null,
    val popularity: Double? = null,
    val origin_country: List<String>? = null,
    val vote_count: Int? = null,
    val first_air_date: String? = null,
    val backdrop_path: String? = null,
    val original_language: String? = null,
    val id: Int? = null,
    val vote_average: Double? = null,
    val overview: String? = null,
    val poster_path: String? = null
)