package com.stathis.domain.model

data class TvSeriesWrapper(
    val series: List<TvSeries>
) : UiModel {
    override fun equalsContent(obj: UiModel): Boolean = when (obj) {
        is TvSeriesWrapper -> series == obj.series
        else -> false
    }
}