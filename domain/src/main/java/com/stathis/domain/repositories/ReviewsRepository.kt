package com.stathis.domain.repositories

import com.stathis.domain.model.reviews.Rating
import com.stathis.domain.model.reviews.Review
import kotlinx.coroutines.flow.Flow

interface ReviewsRepository {

    suspend fun setRatingForSeries(rating: Rating): Flow<Boolean>

    suspend fun getMyRatings(): Flow<List<Rating>>

    suspend fun getRatingsBySeriesId(seriesId: Int): Flow<List<Rating>>

    suspend fun getReviewsForSeries(seriesId: Int): List<Review>
}