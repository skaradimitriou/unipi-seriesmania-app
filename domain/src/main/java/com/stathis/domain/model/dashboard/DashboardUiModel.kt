package com.stathis.domain.model.dashboard

import com.stathis.domain.model.TvSeries
import com.stathis.domain.model.TvSeriesWrapper
import com.stathis.domain.model.UiModel
import com.stathis.domain.model.profile.User

data class DashboardUiModel(
    val profileInfo: User,
    val popularSeries: PopularSeries,
    val topRatedSeries: TopRatedSeries,
    val trendingSeries: TrendingSeries,
    val airingTodaySeries: AiringTodaySeries,
    val watchlist: TvSeriesWrapper? = null,
    val firstPreference: TvSeriesWrapper? = null,
    val secondPreference: TvSeriesWrapper? = null,
    val thirdPreference: TvSeriesWrapper? = null,
)

data class PopularSeries(
    val results: List<TvSeries>
) : UiModel {
    override fun equalsContent(obj: UiModel): Boolean = when (obj) {
        is PopularSeries -> results == obj.results
        else -> false
    }
}

data class TopRatedSeries(
    val results: List<TvSeries>
) : UiModel {
    override fun equalsContent(obj: UiModel): Boolean = when (obj) {
        is TopRatedSeries -> results == obj.results
        else -> false
    }
}

data class TrendingSeries(
    val results: List<TvSeries>
) : UiModel {
    override fun equalsContent(obj: UiModel): Boolean = when (obj) {
        is TrendingSeries -> results == obj.results
        else -> false
    }
}

data class AiringTodaySeries(
    val results: List<TvSeries>
) : UiModel {
    override fun equalsContent(obj: UiModel): Boolean = when (obj) {
        is AiringTodaySeries -> results == obj.results
        else -> false
    }
}