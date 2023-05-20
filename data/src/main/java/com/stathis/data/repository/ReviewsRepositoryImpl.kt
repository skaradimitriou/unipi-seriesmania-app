package com.stathis.data.repository

import com.stathis.data.api.SeriesApi
import com.stathis.data.mappers.ReviewsMapper
import com.stathis.domain.model.reviews.Review
import com.stathis.domain.repositories.ReviewsRepository
import timber.log.Timber
import javax.inject.Inject

class ReviewsRepositoryImpl @Inject constructor(
    private val api: SeriesApi
) : ReviewsRepository {

    override suspend fun getReviewsForSeries(seriesId: Int): List<Review> {
        val request = api.getReviewsForSeries(seriesId)
        return if (request.isSuccessful) {
            val result = request.body()
            ReviewsMapper.toDomainModel(result)
        } else {
            Timber.d("FAILED")
            listOf()
        }
    }
}