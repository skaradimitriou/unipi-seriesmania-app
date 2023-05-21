package com.stathis.data.api

import com.stathis.data.model.CastFeedDto
import com.stathis.data.model.ReviewsFeedDto
import com.stathis.data.model.TvSeriesFeedDto
import com.stathis.data.util.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SeriesApi {

    @GET("tv/popular?$API_KEY")
    suspend fun getPopularSeries(): Response<TvSeriesFeedDto?>

    @GET("tv/top_rated?$API_KEY")
    suspend fun getTopRatedSeries(): Response<TvSeriesFeedDto?>

    @GET("tv/on_the_air?$API_KEY")
    suspend fun getOnTheAirSeries(): Response<TvSeriesFeedDto?>

    @GET("trending/tv/week?$API_KEY")
    suspend fun getTrendingSeries(): Response<TvSeriesFeedDto?>

    @GET("tv/{tvSeriesId}/credits?$API_KEY")
    suspend fun getCastForSeries(@Path("tvSeriesId") tvSeriesId: Int): Response<CastFeedDto?>

    @GET("tv/{tvSeriesId}/reviews?$API_KEY")
    suspend fun getReviewsForSeries(@Path("tvSeriesId") tvSeriesId: Int): Response<ReviewsFeedDto?>

    @GET("tv/{tvSeriesId}/similar?$API_KEY")
    suspend fun getSimilarSeries(@Path("tvSeriesId") tvSeriesId: Int): Response<TvSeriesFeedDto?>

    @GET("tv/{tvSeriesId}/recommendations?$API_KEY")
    suspend fun getRecommendedSeries(@Path("tvSeriesId") tvSeriesId: Int): Response<TvSeriesFeedDto?>
}