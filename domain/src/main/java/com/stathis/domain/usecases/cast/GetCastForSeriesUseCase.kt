package com.stathis.domain.usecases.cast

import com.stathis.domain.model.cast.Cast
import com.stathis.domain.repositories.CastRepository
import com.stathis.domain.usecases.BaseUseCase
import javax.inject.Inject

class GetCastForSeriesUseCase @Inject constructor(
    private val repo: CastRepository
) : BaseUseCase<List<Cast>> {

    override suspend fun invoke(vararg args: Any?): List<Cast> {
        val seriesId = args.getOrNull(0) as? Int ?: 0
        return repo.getCastForSeries(seriesId)
    }
}