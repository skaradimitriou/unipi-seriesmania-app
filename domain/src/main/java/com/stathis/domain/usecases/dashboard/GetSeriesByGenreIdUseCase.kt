package com.stathis.domain.usecases.dashboard

import com.stathis.domain.model.TvSeries
import com.stathis.domain.repositories.SeriesRepository
import com.stathis.domain.usecases.BaseUseCase
import javax.inject.Inject

class GetSeriesByGenreIdUseCase @Inject constructor(
    private val repo: SeriesRepository
) : BaseUseCase<List<TvSeries>> {

    override suspend fun invoke(vararg args: Any?): List<TvSeries> {
        val genreId = args.getOrNull(0) as? Int ?: 0
        return repo.getSeriesByGenreId(genreId)
    }
}