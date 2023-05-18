package com.stathis.domain.repositories

import com.stathis.domain.model.TvSeries

interface DashboardRepository {

    suspend fun getPopularSeries(): List<TvSeries>
}