package com.stathis.core.callbacks

import com.stathis.domain.model.TvSeries

interface DashboardCallback {
    fun onProfileClick()
    fun onSeriesClick(model: TvSeries)
    fun openAllTopRatedSeries()
    fun openAllTrendingSeries()
    fun openAllAiringTodaySeries()
    fun openMyPreferencesSeries(id: Int)
}