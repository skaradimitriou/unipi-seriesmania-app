package com.stathis.domain.combiners

import com.stathis.domain.model.dashboard.AiringTodaySeries
import com.stathis.domain.model.dashboard.DashboardUiModel
import com.stathis.domain.model.dashboard.PopularSeries
import com.stathis.domain.model.dashboard.TopRatedSeries
import com.stathis.domain.model.dashboard.TrendingSeries
import com.stathis.domain.usecases.dashboard.GetAiringTodaySeriesUseCase
import com.stathis.domain.usecases.dashboard.GetPopularSeriesUseCase
import com.stathis.domain.usecases.dashboard.GetTopRatedSeriesUseCase
import com.stathis.domain.usecases.dashboard.GetTrendingSeriesUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class DashboardDataCombiner @Inject constructor(
    private val popularSeriesUseCase: GetPopularSeriesUseCase,
    private val topRatedSeriesUseCase: GetTopRatedSeriesUseCase,
    private val trendingSeriesUseCase: GetTrendingSeriesUseCase,
    private val airingTodaySeriesUseCase: GetAiringTodaySeriesUseCase,
) : BaseCombiner<DashboardUiModel> {

    override suspend fun invoke(): DashboardUiModel = coroutineScope {
        val popularSeries = PopularSeries(
            async { popularSeriesUseCase.invoke() }.await()
        )
        val topRatedSeries = TopRatedSeries(
            async { topRatedSeriesUseCase.invoke() }.await()
        )
        val trendingSeries = TrendingSeries(
            async { trendingSeriesUseCase.invoke() }.await()
        )
        val airingTodaySeries = AiringTodaySeries(
            async { airingTodaySeriesUseCase.invoke() }.await()
        )

        return@coroutineScope DashboardUiModel(
            popularSeries, topRatedSeries, trendingSeries, airingTodaySeries
        )
    }
}