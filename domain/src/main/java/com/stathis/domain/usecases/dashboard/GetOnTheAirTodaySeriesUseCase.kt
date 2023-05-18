package com.stathis.domain.usecases.dashboard

import com.stathis.domain.model.TvSeries
import com.stathis.domain.repositories.DashboardRepository
import com.stathis.domain.usecases.BaseUseCase
import javax.inject.Inject

class GetOnTheAirTodaySeriesUseCase @Inject constructor(
    private val repo: DashboardRepository
) : BaseUseCase<List<TvSeries>> {

    override suspend fun invoke(vararg args: Any?): List<TvSeries> = repo.getTopRatedSeries()
}