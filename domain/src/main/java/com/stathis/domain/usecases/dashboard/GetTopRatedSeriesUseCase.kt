package com.stathis.domain.usecases.dashboard

import com.stathis.domain.model.TvSeries
import com.stathis.domain.repositories.SeriesRepository
import com.stathis.domain.usecases.BaseUseCase
import javax.inject.Inject

class GetTopRatedSeriesUseCase @Inject constructor(
    private val repo: SeriesRepository
) : BaseUseCase<List<TvSeries>> {

    override suspend fun invoke(vararg args: Any?): List<TvSeries> = repo.getTopRatedSeries()
}