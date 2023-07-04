package com.stathis.domain.usecases.ratings

import com.stathis.domain.model.reviews.Rating
import com.stathis.domain.repositories.ReviewsRepository
import com.stathis.domain.usecases.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchUserReviewsBySeriesIdUseCase @Inject constructor(
    private val repo: ReviewsRepository
) : BaseUseCase<Flow<List<Rating>>> {

    override suspend fun invoke(vararg args: Any?): Flow<List<Rating>> {
        val seriesId = args.getOrNull(0) as? Int ?: 0
        return repo.getRatingsBySeriesId(seriesId)
    }
}