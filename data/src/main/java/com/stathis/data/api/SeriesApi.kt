package com.stathis.data.api

import com.stathis.data.model.TvSeriesFeedDto
import com.stathis.data.util.API_KEY
import retrofit2.Response
import retrofit2.http.GET

interface SeriesApi {

    @GET("tv/popular?$API_KEY")
    suspend fun getPopularSeries(): Response<TvSeriesFeedDto?>
}