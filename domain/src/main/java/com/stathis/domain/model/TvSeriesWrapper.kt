package com.stathis.domain.model

data class TvSeriesWrapper(
    val series: List<TvSeries>,
    val title: String? = "Series",
) : UiModel {
    constructor() : this(listOf(),"")

    override fun equalsContent(obj: UiModel): Boolean = when (obj) {
        is TvSeriesWrapper -> series == obj.series
        else -> false
    }
}