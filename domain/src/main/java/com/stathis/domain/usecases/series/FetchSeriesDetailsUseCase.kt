package com.stathis.domain.usecases.series

import com.stathis.domain.model.TvSeriesDetails
import com.stathis.domain.repositories.SeriesRepository
import com.stathis.domain.usecases.BaseUseCase
import javax.inject.Inject

class FetchSeriesDetailsUseCase @Inject constructor(
    private val repo: SeriesRepository
) : BaseUseCase<TvSeriesDetails> {

    override suspend fun invoke(vararg args: Any?): TvSeriesDetails {
        val seriesId = args.getOrNull(0) as? Int ?: 0
        return repo.getSeriesDetails(seriesId)
    }
}