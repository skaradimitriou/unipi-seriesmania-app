package com.stathis.data.repository

import com.stathis.core.ext.getAndMapResponse
import com.stathis.data.api.SeriesApi
import com.stathis.data.mappers.GenresMapper
import com.stathis.data.mappers.TvSeriesMapper
import com.stathis.domain.model.TvSeries
import com.stathis.domain.model.genres.Genre
import com.stathis.domain.repositories.SeriesRepository
import javax.inject.Inject

class SeriesRepositoryImpl @Inject constructor(
    private val api: SeriesApi
) : SeriesRepository {

    override suspend fun getPopularSeries(): List<TvSeries> = getAndMapResponse(
        call = { api.getPopularSeries() },
        mapper = { TvSeriesMapper.toDomainModel(it).results }
    )

    override suspend fun getTopRatedSeries(): List<TvSeries> = getAndMapResponse(
        call = { api.getTopRatedSeries() },
        mapper = { TvSeriesMapper.toDomainModel(it).results }
    )

    override suspend fun getOnTheAirSeries(): List<TvSeries> = getAndMapResponse(
        call = { api.getOnTheAirSeries() },
        mapper = { TvSeriesMapper.toDomainModel(it).results }
    )

    override suspend fun getTrendingSeries(): List<TvSeries> = getAndMapResponse(
        call = { api.getTrendingSeries() },
        mapper = { TvSeriesMapper.toDomainModel(it).results }
    )

    override suspend fun getSeriesGenres(): List<Genre> = getAndMapResponse(
        call = { api.getSeriesGenres() },
        mapper = { GenresMapper.toDomainModel(it) }
    )

    override suspend fun getSimilarSeries(seriesId: Int): List<TvSeries> = getAndMapResponse(
        call = { api.getSimilarSeries(seriesId) },
        mapper = { TvSeriesMapper.toDomainModel(it).results }
    )

    override suspend fun getRecommendedSeries(seriesId: Int): List<TvSeries> = getAndMapResponse(
        call = { api.getRecommendedSeries(seriesId) },
        mapper = { TvSeriesMapper.toDomainModel(it).results }
    )
}