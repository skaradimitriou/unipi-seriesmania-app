package com.stathis.data.repository

import com.stathis.data.api.SeriesApi
import com.stathis.data.mappers.GenresMapper
import com.stathis.data.mappers.TvSeriesMapper
import com.stathis.domain.model.TvSeries
import com.stathis.domain.model.genres.Genre
import com.stathis.domain.repositories.SeriesRepository
import timber.log.Timber
import javax.inject.Inject

class SeriesRepositoryImpl @Inject constructor(
    private val api: SeriesApi
) : SeriesRepository {

    override suspend fun getPopularSeries(): List<TvSeries> {
        val request = api.getPopularSeries()
        return if (request.isSuccessful) {
            val result = request.body()
            val data = TvSeriesMapper.toDomainModel(result)
            data.results
        } else {
            Timber.d("FAILED")
            listOf()
        }
    }

    override suspend fun getTopRatedSeries(): List<TvSeries> {
        val request = api.getTopRatedSeries()
        return if (request.isSuccessful) {
            val result = request.body()
            val data = TvSeriesMapper.toDomainModel(result)
            data.results
        } else {
            Timber.d("FAILED")
            listOf()
        }
    }

    override suspend fun getOnTheAirSeries(): List<TvSeries> {
        val request = api.getOnTheAirSeries()
        return if (request.isSuccessful) {
            val result = request.body()
            val data = TvSeriesMapper.toDomainModel(result)
            data.results
        } else {
            Timber.d("FAILED")
            listOf()
        }
    }

    override suspend fun getTrendingSeries(): List<TvSeries> {
        val request = api.getTrendingSeries()
        return if (request.isSuccessful) {
            val result = request.body()
            val data = TvSeriesMapper.toDomainModel(result)
            data.results
        } else {
            Timber.d("FAILED")
            listOf()
        }
    }

    override suspend fun getSeriesGenres(): List<Genre> {
        val request = api.getSeriesGenres()
        return if (request.isSuccessful) {
            val result = request.body()
            GenresMapper.toDomainModel(result)
        } else {
            Timber.d("FAILED")
            listOf()
        }
    }

    override suspend fun getSimilarSeries(seriesId: Int): List<TvSeries> {
        val request = api.getSimilarSeries(seriesId)
        return if (request.isSuccessful) {
            val result = request.body()
            val data = TvSeriesMapper.toDomainModel(result)
            data.results
        } else {
            Timber.d("FAILED")
            listOf()
        }
    }

    override suspend fun getRecommendedSeries(seriesId: Int): List<TvSeries> {
        val request = api.getRecommendedSeries(seriesId)
        return if (request.isSuccessful) {
            val result = request.body()
            val data = TvSeriesMapper.toDomainModel(result)
            data.results
        } else {
            Timber.d("FAILED")
            listOf()
        }
    }
}