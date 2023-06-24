package com.stathis.data.repository

import com.stathis.core.ext.getAndMapResponse
import com.stathis.data.api.SeriesApi
import com.stathis.data.mappers.TvSeriesDetailsMapper
import com.stathis.data.mappers.TvSeriesMapper
import com.stathis.domain.model.TvSeries
import com.stathis.domain.model.TvSeriesDetails
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

    override suspend fun getSimilarSeries(seriesId: Int): List<TvSeries> = getAndMapResponse(
        call = { api.getSimilarSeries(seriesId) },
        mapper = { TvSeriesMapper.toDomainModel(it).results }
    )

    override suspend fun getRecommendedSeries(seriesId: Int): List<TvSeries> = getAndMapResponse(
        call = { api.getRecommendedSeries(seriesId) },
        mapper = { TvSeriesMapper.toDomainModel(it).results }
    )

    override suspend fun searchForSeries(query: String): List<TvSeries> = getAndMapResponse(
        call = { api.searchForSeries(query) },
        mapper = { TvSeriesMapper.toDomainModel(it).results }
    )

    override suspend fun getSeriesByGenreId(genreId: Int): List<TvSeries> = getAndMapResponse(
        call = { api.getPagedResultsForThisGenre(genreId.toString()) },
        mapper = { TvSeriesMapper.toDomainModel(it).results }
    )

    override suspend fun getSeriesDetails(seriesId: Int): TvSeriesDetails = getAndMapResponse(
        call = { api.getSeriesDetails(seriesId.toString()) },
        mapper = { TvSeriesDetailsMapper.toDomainModel(it) }
    )
}