package com.stathis.data.repository

import com.stathis.data.api.SeriesApi
import com.stathis.data.mappers.CastMapper
import com.stathis.domain.model.cast.Cast
import com.stathis.domain.repositories.CastRepository
import timber.log.Timber
import javax.inject.Inject

class CastRepositoryImpl @Inject constructor(
    private val api: SeriesApi
) : CastRepository {

    override suspend fun getCastForSeries(seriesId: Int): List<Cast> {
        val request = api.getCastForSeries(seriesId)
        return if (request.isSuccessful) {
            val result = request.body()
            CastMapper.toDomainModel(result)
        } else {
            Timber.d("FAILED")
            listOf()
        }
    }
}