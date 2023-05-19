package com.stathis.domain.repositories

import com.stathis.domain.model.TvSeries

interface DashboardRepository {

    suspend fun getPopularSeries(): List<TvSeries>

    suspend fun getTopRatedSeries(): List<TvSeries>

    suspend fun getOnTheAirSeries(): List<TvSeries>

    suspend fun getTrendingSeries(): List<TvSeries>
}