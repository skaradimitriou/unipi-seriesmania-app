package com.stathis.domain.usecases.ratings

import com.stathis.domain.repositories.ReviewsRepository
import com.stathis.domain.usecases.BaseUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchIfUserHasRatedSeriesUseCase @Inject constructor(
    private val repo: ReviewsRepository
) : BaseUseCase<Flow<Boolean>> {

    override suspend fun invoke(vararg args: Any?): Flow<Boolean> = flow {
        val seriesId = args.getOrNull(0) as? String ?: ""
        repo.getMyRatings().collect { list ->
            val containsElement = list.any { it.seriesId == seriesId }
            emit(containsElement)
        }
    }
}