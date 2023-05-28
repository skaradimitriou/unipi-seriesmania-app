package com.stathis.data.repository

import com.stathis.core.ext.getAndMapResponse
import com.stathis.data.api.SeriesApi
import com.stathis.data.mappers.ReviewsMapper
import com.stathis.domain.model.reviews.Review
import com.stathis.domain.repositories.ReviewsRepository
import javax.inject.Inject

class ReviewsRepositoryImpl @Inject constructor(
    private val api: SeriesApi
) : ReviewsRepository {

    override suspend fun getReviewsForSeries(seriesId: Int): List<Review> {
        return getAndMapResponse(
            call = { api.getReviewsForSeries(seriesId) },
            mapper = { ReviewsMapper.toDomainModel(it) }
        )
    }
}