package com.stathis.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TvSeries(
    val original_name: String,
    val genre_ids: List<Int>,
    val name: String,
    val popularity: Double,
    val origin_country: List<String>,
    val vote_count: Int,
    val first_air_date: String,
    val backdrop_path: String,
    val original_language: String,
    val id: Int,
    val vote_average: Double,
    val overview: String,
    val poster_path: String,
    val genres: String
) : UiModel, Parcelable {
    constructor() : this(
        "",
        listOf(),
        "",
        0.0,
        listOf(),
        0,
        "",
        "",
        "",
        0,
        0.0,
        "",
        "",
        ""
    )

    override fun equalsContent(obj: UiModel): Boolean = when (obj) {
        is TvSeries -> id == obj.id && name == obj.name
        else -> false
    }
}
