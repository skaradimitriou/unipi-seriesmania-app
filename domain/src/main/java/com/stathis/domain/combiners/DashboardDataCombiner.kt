package com.stathis.domain.combiners

import com.stathis.domain.model.TvSeriesWrapper
import com.stathis.domain.model.dashboard.*
import com.stathis.domain.usecases.dashboard.GetAiringTodaySeriesUseCase
import com.stathis.domain.usecases.dashboard.GetPopularSeriesUseCase
import com.stathis.domain.usecases.dashboard.GetTopRatedSeriesUseCase
import com.stathis.domain.usecases.dashboard.GetTrendingSeriesUseCase
import com.stathis.domain.usecases.profile.GetProfileInfoUseCase
import com.stathis.domain.usecases.watchlist.FetchWatchlistUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class DashboardDataCombiner @Inject constructor(
    private val profileInfoUseCase: GetProfileInfoUseCase,
    private val popularSeriesUseCase: GetPopularSeriesUseCase,
    private val topRatedSeriesUseCase: GetTopRatedSeriesUseCase,
    private val trendingSeriesUseCase: GetTrendingSeriesUseCase,
    private val airingTodaySeriesUseCase: GetAiringTodaySeriesUseCase,
    private val fetchWatchlistUseCase: FetchWatchlistUseCase
) : BaseCombiner<DashboardUiModel> {

    override suspend fun invoke(vararg args: Any?): DashboardUiModel = coroutineScope {
        val profile = async { profileInfoUseCase.invoke() }.await()

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

        var watchlist: TvSeriesWrapper? = null
        fetchWatchlistUseCase.invoke().collect {
            watchlist = TvSeriesWrapper(it)
        }

        return@coroutineScope DashboardUiModel(
            profile, popularSeries, topRatedSeries, trendingSeries, airingTodaySeries, watchlist
        )
    }
}