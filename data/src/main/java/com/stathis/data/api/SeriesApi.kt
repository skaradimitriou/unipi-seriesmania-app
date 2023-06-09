package com.stathis.data.api

import com.stathis.data.model.*
import com.stathis.data.util.API_KEY
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface SeriesApi {

    @GET("tv/popular?$API_KEY")
    suspend fun getPopularSeries(
        @Query("page") page: Int = 1
    ): Response<TvSeriesFeedDto?>

    @GET("tv/top_rated?$API_KEY")
    suspend fun getTopRatedSeries(
        @Query("page") page: Int = 1
    ): Response<TvSeriesFeedDto?>

    @GET("tv/on_the_air?$API_KEY")
    suspend fun getOnTheAirSeries(
        @Query("page") page: Int = 1
    ): Response<TvSeriesFeedDto?>

    @GET("trending/tv/week?$API_KEY")
    suspend fun getTrendingSeries(
        @Query("page") page: Int = 1
    ): Response<TvSeriesFeedDto?>

    @GET("discover/tv?$API_KEY")
    suspend fun getPagedResultsForThisGenre(
        @Query("with_genres") genreId: String,
        @Query("page") page: Int = 1
    ): Response<TvSeriesFeedDto?>

    @GET("tv/{seriesId}?$API_KEY")
    suspend fun getSeriesDetails(
        @Path("seriesId") seriesId: String
    ): Response<TvSeriesDetailsDto?>

    @GET("tv/{tvSeriesId}/credits?$API_KEY")
    suspend fun getCastForSeries(@Path("tvSeriesId") tvSeriesId: Int): Response<CastFeedDto?>

    @GET("tv/{tvSeriesId}/reviews?$API_KEY")
    suspend fun getReviewsForSeries(@Path("tvSeriesId") tvSeriesId: Int): Response<ReviewsFeedDto?>

    @GET("tv/{tvSeriesId}/similar?$API_KEY")
    suspend fun getSimilarSeries(@Path("tvSeriesId") tvSeriesId: Int): Response<TvSeriesFeedDto?>

    @GET("tv/{tvSeriesId}/recommendations?$API_KEY")
    suspend fun getRecommendedSeries(@Path("tvSeriesId") tvSeriesId: Int): Response<TvSeriesFeedDto?>

    @GET("search/tv?$API_KEY")
    suspend fun searchForSeries(
        @Query("query") query: String,
        @Query("include_adult") includeAdult: Boolean = false
    ): Response<TvSeriesFeedDto?>

    @GET("person/{person_id}?$API_KEY")
    suspend fun getPersonDetails(@Path("person_id") person_id: Int): Response<ActorDetailsDto?>

    @GET("person/{person_id}/tv_credits?$API_KEY")
    suspend fun getActorsKnownMovies(@Path("person_id") person_id: Int): Response<KnownSeriesFeedDto?>

    @GET("authentication/guest_session/new?$API_KEY")
    suspend fun createGuesSession(): Response<GuestSessionResponseDto?>

    @POST("tv/{tvSeriesId}/rating?$API_KEY")
    suspend fun addRatingForSeries(
        @Path("tvSeriesId") series_id: String,
        @Query("guest_session_id") sessionId: String,
        @Body ratingRequest: UpdateRatingRequest
    ): Response<RatingResponseDto?>
}