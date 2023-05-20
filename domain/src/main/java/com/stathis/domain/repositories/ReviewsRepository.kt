package com.stathis.domain.repositories

import com.stathis.domain.model.reviews.Review

interface ReviewsRepository {

    suspend fun getReviewsForSeries(seriesId: Int): List<Review>
}