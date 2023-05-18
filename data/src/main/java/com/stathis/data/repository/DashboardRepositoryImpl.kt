package com.stathis.data.repository

import com.stathis.data.api.SeriesApi
import com.stathis.data.mappers.TvSeriesMapper
import com.stathis.domain.model.TvSeries
import com.stathis.domain.repositories.DashboardRepository
import timber.log.Timber
import javax.inject.Inject

class DashboardRepositoryImpl @Inject constructor(
    private val api: SeriesApi
) : DashboardRepository {

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

    override suspend fun getAiringTodaySeries(): List<TvSeries> {
        val request = api.getAiringTodaySeries()
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