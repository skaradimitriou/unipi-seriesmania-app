package com.stathis.domain.repositories

import com.stathis.domain.model.TvSeries
import com.stathis.domain.model.TvSeriesDetails

interface SeriesRepository {

    suspend fun getPopularSeries(): List<TvSeries>

    suspend fun getTopRatedSeries(): List<TvSeries>

    suspend fun getOnTheAirSeries(): List<TvSeries>

    suspend fun getTrendingSeries(): List<TvSeries>

    suspend fun getSimilarSeries(seriesId: Int): List<TvSeries>

    suspend fun getRecommendedSeries(seriesId: Int): List<TvSeries>

    suspend fun searchForSeries(query: String): List<TvSeries>

    suspend fun getSeriesByGenreId(genreId: Int): List<TvSeries>

    suspend fun getSeriesDetails(seriesId: Int): TvSeriesDetails
}