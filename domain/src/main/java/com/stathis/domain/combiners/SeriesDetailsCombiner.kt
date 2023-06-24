package com.stathis.domain.combiners

import com.stathis.domain.model.details.*
import com.stathis.domain.usecases.cast.GetCastForSeriesUseCase
import com.stathis.domain.usecases.general.GetRecommendedSeriesUseCase
import com.stathis.domain.usecases.general.GetSimilarSeriesUseCase
import com.stathis.domain.usecases.reviews.GetReviewsForSeriesUseCase
import com.stathis.domain.usecases.series.FetchSeriesDetailsUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class SeriesDetailsCombiner @Inject constructor(
    private val detailsUseCase: FetchSeriesDetailsUseCase,
    private val castUseCase: GetCastForSeriesUseCase,
    private val reviewsUseCase: GetReviewsForSeriesUseCase,
    private val similarSeriesUseCase: GetSimilarSeriesUseCase,
    private val recommendedUseCase: GetRecommendedSeriesUseCase,
) : BaseCombiner<DetailsUiModel> {

    override suspend fun invoke(vararg args: Any?): DetailsUiModel = coroutineScope {
        val seriesId = args.getOrNull(0) as? Int ?: 0

        val details = async { detailsUseCase.invoke(seriesId) }.await()

        val cast = CastModel(
            async { castUseCase.invoke(seriesId) }.await()
        )
        val reviews = ReviewsModel(
            async { reviewsUseCase.invoke(seriesId) }.await()
        )
        val similarSeries = SimilarModel(
            async { similarSeriesUseCase.invoke(seriesId) }.await()
        )
        val recommendedSeries = RecommendationModel(
            async { recommendedUseCase.invoke(seriesId) }.await()
        )

        return@coroutineScope DetailsUiModel(
            details,
            cast,
            reviews,
            similarSeries,
            recommendedSeries
        )
    }
}