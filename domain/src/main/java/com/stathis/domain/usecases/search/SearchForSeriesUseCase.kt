package com.stathis.domain.usecases.search

import com.stathis.domain.model.TvSeries
import com.stathis.domain.repositories.SeriesRepository
import com.stathis.domain.usecases.BaseUseCase
import javax.inject.Inject

class SearchForSeriesUseCase @Inject constructor(
    private val repo: SeriesRepository
) : BaseUseCase<List<TvSeries>> {

    override suspend fun invoke(vararg args: Any?): List<TvSeries> {
        val query = args.getOrNull(0) as? String? ?: ""
        return repo.searchForSeries(query)
    }
}