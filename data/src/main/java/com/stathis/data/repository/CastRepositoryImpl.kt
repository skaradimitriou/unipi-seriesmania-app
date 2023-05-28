package com.stathis.data.repository

import com.stathis.core.ext.getAndMapResponse
import com.stathis.data.api.SeriesApi
import com.stathis.data.mappers.ActorDetailsMapper
import com.stathis.data.mappers.CastMapper
import com.stathis.data.mappers.KnownSeriesMapper
import com.stathis.domain.model.TvSeries
import com.stathis.domain.model.cast.ActorDetails
import com.stathis.domain.model.cast.Cast
import com.stathis.domain.repositories.CastRepository
import javax.inject.Inject

class CastRepositoryImpl @Inject constructor(
    private val api: SeriesApi
) : CastRepository {

    override suspend fun getCastForSeries(seriesId: Int): List<Cast> = getAndMapResponse(
        call = { api.getCastForSeries(seriesId) },
        mapper = { CastMapper.toDomainModel(it) }
    )

    override suspend fun getSeriesForActor(actorId: Int): List<TvSeries> = getAndMapResponse(
        call = { api.getActorsKnownMovies(actorId) },
        mapper = { KnownSeriesMapper.toDomainModel(it) }
    )

    override suspend fun getPersonDetails(actorId: Int): ActorDetails = getAndMapResponse(
        call = { api.getPersonDetails(actorId) },
        mapper = { ActorDetailsMapper.toDomainModel(it) }
    )
}