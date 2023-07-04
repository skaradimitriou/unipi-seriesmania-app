package com.stathis.domain.combiners

import com.stathis.domain.model.TvSeriesWrapper
import com.stathis.domain.model.dashboard.AiringTodaySeries
import com.stathis.domain.model.dashboard.DashboardUiModel
import com.stathis.domain.model.dashboard.PopularSeries
import com.stathis.domain.model.dashboard.TopRatedSeries
import com.stathis.domain.model.dashboard.TrendingSeries
import com.stathis.domain.usecases.dashboard.GetAiringTodaySeriesUseCase
import com.stathis.domain.usecases.dashboard.GetPopularSeriesUseCase
import com.stathis.domain.usecases.dashboard.GetSeriesByGenreIdUseCase
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
    private val fetchWatchlistUseCase: FetchWatchlistUseCase,
    private val seriesByGenreIdUseCase: GetSeriesByGenreIdUseCase
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
        async {
            fetchWatchlistUseCase.invoke().collect {
                if (it.isNotEmpty()) {
                    watchlist = TvSeriesWrapper(it, "Watchlist")
                }
            }
        }.await()

        val firstPreference = async {
            profile.preferences.getOrNull(0)?.let {
                TvSeriesWrapper(
                    series = seriesByGenreIdUseCase.invoke(it.id),
                    title = "${it.name} series",
                    btnTitle = "ALL",
                    genreId = it.id
                )
            }
        }.await()

        val secondPreference = async {
            profile.preferences.getOrNull(1)?.let {
                TvSeriesWrapper(
                    series = seriesByGenreIdUseCase.invoke(it.id),
                    title = "${it.name} series",
                    btnTitle = "ALL",
                    genreId = it.id
                )
            }
        }.await()

        val thirdPreference = async {
            profile.preferences.getOrNull(2)?.let {
                TvSeriesWrapper(
                    series = seriesByGenreIdUseCase.invoke(it.id),
                    title = "${it.name} series",
                    btnTitle = "ALL",
                    genreId = it.id
                )
            }
        }.await()

        return@coroutineScope DashboardUiModel(
            profile,
            popularSeries,
            topRatedSeries,
            trendingSeries,
            airingTodaySeries,
            watchlist,
            firstPreference,
            secondPreference,
            thirdPreference
        )
    }
}