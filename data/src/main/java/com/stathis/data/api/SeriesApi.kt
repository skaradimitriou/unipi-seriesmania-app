package com.stathis.data.api

import com.stathis.data.model.TvSeriesFeedDto
import com.stathis.data.util.API_KEY
import retrofit2.Response
import retrofit2.http.GET

interface SeriesApi {

    @GET("tv/popular?$API_KEY")
    suspend fun getPopularSeries(): Response<TvSeriesFeedDto?>

    @GET("tv/top_rated?$API_KEY")
    suspend fun getTopRatedSeries(): Response<TvSeriesFeedDto?>

    @GET("tv/on_the_air?$API_KEY")
    suspend fun getOnTheAirSeries(): Response<TvSeriesFeedDto?>

    @GET("trending/tv/week?$API_KEY")
    suspend fun getTrendingSeries(): Response<TvSeriesFeedDto?>
}