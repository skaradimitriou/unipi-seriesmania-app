package com.stathis.domain.usecases.general

import com.stathis.domain.model.TvSeries
import com.stathis.domain.repositories.SeriesRepository
import com.stathis.domain.usecases.BaseUseCase
import javax.inject.Inject

class GetRecommendedSeriesUseCase @Inject constructor(
    private val repo: SeriesRepository
) : BaseUseCase<List<TvSeries>> {

    override suspend fun invoke(vararg args: Any?): List<TvSeries> {
        val seriesId = args.getOrNull(0) as? Int ?: 0
        return repo.getRecommendedSeries(seriesId)
    }
}