package com.stathis.domain.repositories

import com.stathis.domain.model.TvSeries

interface SeriesRepository {

    suspend fun getPopularSeries(): List<TvSeries>

    suspend fun getTopRatedSeries(): List<TvSeries>

    suspend fun getOnTheAirSeries(): List<TvSeries>

    suspend fun getTrendingSeries(): List<TvSeries>

    suspend fun getSimilarSeries(seriesId: Int): List<TvSeries>

    suspend fun getRecommendedSeries(seriesId: Int): List<TvSeries>
}