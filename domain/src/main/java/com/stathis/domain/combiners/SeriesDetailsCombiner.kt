package com.stathis.domain.combiners

import com.stathis.domain.model.details.CastModel
import com.stathis.domain.model.details.DetailsUiModel
import com.stathis.domain.model.details.ReviewsModel
import com.stathis.domain.usecases.cast.GetCastForSeriesUseCase
import com.stathis.domain.usecases.reviews.GetReviewsForSeriesUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class SeriesDetailsCombiner @Inject constructor(
    private val castUseCase: GetCastForSeriesUseCase,
    private val reviewsUseCase: GetReviewsForSeriesUseCase
) : BaseCombiner<DetailsUiModel> {

    override suspend fun invoke(vararg args: Any?): DetailsUiModel = coroutineScope {
        val seriesId = args.getOrNull(0) as? Int ?: 0
        val cast = CastModel(
            async { castUseCase.invoke(seriesId) }.await()
        )
        val reviews = ReviewsModel(
            async { reviewsUseCase.invoke(seriesId) }.await()
        )

        return@coroutineScope DetailsUiModel(
            cast = cast,
            reviews = reviews
        )
    }
}