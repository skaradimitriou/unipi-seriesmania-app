package com.stathis.domain.combiners

import com.stathis.domain.model.details.CastModel
import com.stathis.domain.model.details.DetailsUiModel
import com.stathis.domain.model.details.RecommendationModel
import com.stathis.domain.model.details.ReviewsModel
import com.stathis.domain.model.details.SimilarModel
import com.stathis.domain.model.reviews.Review
import com.stathis.domain.usecases.cast.GetCastForSeriesUseCase
import com.stathis.domain.usecases.general.GetRecommendedSeriesUseCase
import com.stathis.domain.usecases.general.GetSimilarSeriesUseCase
import com.stathis.domain.usecases.ratings.FetchIfUserHasRatedSeriesUseCase
import com.stathis.domain.usecases.ratings.FetchUserReviewsBySeriesIdUseCase
import com.stathis.domain.usecases.reviews.GetReviewsForSeriesUseCase
import com.stathis.domain.usecases.series.FetchSeriesDetailsUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class SeriesDetailsCombiner @Inject constructor(
    private val detailsUseCase: FetchSeriesDetailsUseCase,
    private val castUseCase: GetCastForSeriesUseCase,
    private val reviewsUseCase: GetReviewsForSeriesUseCase,
    private val similarSeriesUseCase: GetSimilarSeriesUseCase,
    private val recommendedUseCase: GetRecommendedSeriesUseCase,
    private val fetchIfIHaveRatedThisSeries: FetchIfUserHasRatedSeriesUseCase,
    private val userReviewsUseCase: FetchUserReviewsBySeriesIdUseCase
) : BaseCombiner<DetailsUiModel> {

    override suspend fun invoke(vararg args: Any?): DetailsUiModel = coroutineScope {
        val seriesId = args.getOrNull(0) as? Int ?: 0

        val details = async { detailsUseCase.invoke(seriesId) }.await()

        val cast = CastModel(
            async { castUseCase.invoke(seriesId) }.await()
        )

        val userReviews = userReviewsUseCase.invoke(seriesId).firstOrNull()?.filter {
            it.review.isNotEmpty()
        }?.map {
            Review(author = it.username, content = it.review)
        } ?: listOf()

        val reviews = async { reviewsUseCase.invoke(seriesId) }.await()

        val similarSeries = SimilarModel(
            async { similarSeriesUseCase.invoke(seriesId) }.await()
        )
        val recommendedSeries = RecommendationModel(
            async { recommendedUseCase.invoke(seriesId) }.await()
        )

        val allReviews = ReviewsModel(reviews.plus(userReviews))

        return@coroutineScope DetailsUiModel(
            details,
            cast,
            fetchIfIHaveRatedThisSeries.invoke(seriesId.toString()).first(),
            allReviews,
            similarSeries,
            recommendedSeries
        )
    }
}