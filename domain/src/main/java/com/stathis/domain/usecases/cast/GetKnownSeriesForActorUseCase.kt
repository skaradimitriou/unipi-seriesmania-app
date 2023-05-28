package com.stathis.domain.usecases.cast

import com.stathis.domain.model.TvSeries
import com.stathis.domain.repositories.CastRepository
import com.stathis.domain.usecases.BaseUseCase
import javax.inject.Inject

class GetKnownSeriesForActorUseCase @Inject constructor(
    private val repo: CastRepository
) : BaseUseCase<List<TvSeries>> {

    override suspend fun invoke(vararg args: Any?): List<TvSeries> {
        val personId = args.getOrNull(0) as? Int? ?: 0
        return repo.getSeriesForActor(personId)
    }
}