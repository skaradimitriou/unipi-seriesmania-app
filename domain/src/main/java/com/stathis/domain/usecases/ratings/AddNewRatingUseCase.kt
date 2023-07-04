package com.stathis.domain.usecases.ratings

import com.stathis.domain.model.reviews.Rating
import com.stathis.domain.repositories.ReviewsRepository
import com.stathis.domain.usecases.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddNewRatingUseCase @Inject constructor(
    private val repo: ReviewsRepository
) : BaseUseCase<Flow<Boolean>> {

    override suspend fun invoke(vararg args: Any?): Flow<Boolean> {
        val ratingModel = args.getOrNull(0) as Rating
        return repo.setRatingForSeries(ratingModel)
    }
}