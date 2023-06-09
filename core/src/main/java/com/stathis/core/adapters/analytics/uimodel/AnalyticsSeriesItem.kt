package com.stathis.core.adapters.analytics.uimodel

import com.stathis.domain.model.TvSeriesDetails
import com.stathis.domain.model.UiModel

data class AnalyticsSeriesItem(
    val series: List<TvSeriesDetails>
) : UiModel {
    override fun equalsContent(obj: UiModel) = when (obj) {
        is AnalyticsSeriesItem -> series == obj.series

        else -> false
    }
}