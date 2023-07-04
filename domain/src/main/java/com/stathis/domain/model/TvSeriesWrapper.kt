package com.stathis.domain.model

data class TvSeriesWrapper(
    val series: List<TvSeries>,
    val title: String? = "Series",
    val btnTitle: String? = null,
    val genreId: Int? = null
) : UiModel {
    constructor() : this(listOf(), "", "", 0)

    override fun equalsContent(obj: UiModel): Boolean = when (obj) {
        is TvSeriesWrapper -> series == obj.series
        else -> false
    }
}