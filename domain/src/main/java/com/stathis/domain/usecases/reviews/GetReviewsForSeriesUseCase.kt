package com.stathis.domain.usecases.reviews

import com.stathis.domain.model.reviews.Review
import com.stathis.domain.repositories.ReviewsRepository
import com.stathis.domain.usecases.BaseUseCase
import javax.inject.Inject

class GetReviewsForSeriesUseCase @Inject constructor(
    private val repo: ReviewsRepository
) : BaseUseCase<List<Review>> {

    override suspend fun invoke(vararg args: Any?): List<Review> {
        val seriesId = args.getOrNull(0) as? Int ?: 0
        return repo.getReviewsForSeries(seriesId)
    }
}