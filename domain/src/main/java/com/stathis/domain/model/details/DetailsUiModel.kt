package com.stathis.domain.model.details

import com.stathis.domain.model.TvSeries
import com.stathis.domain.model.TvSeriesDetails
import com.stathis.domain.model.UiModel
import com.stathis.domain.model.cast.Cast
import com.stathis.domain.model.reviews.Review

data class DetailsUiModel(
    val details: TvSeriesDetails,
    val cast: CastModel,
    val ratingMadeForThisSeries: Boolean,
    val reviews: ReviewsModel,
    val similar: SimilarModel,
    val recommendations: RecommendationModel,
) : UiModel {
    override fun equalsContent(obj: UiModel): Boolean = when (obj) {
        is DetailsUiModel -> {
            details == obj.details && cast == obj.cast && reviews == obj.reviews
                    && similar == obj.similar && recommendations == obj.recommendations
                    && ratingMadeForThisSeries == obj.ratingMadeForThisSeries
        }

        else -> false
    }
}

data class CastModel(
    val results: List<Cast>
) : UiModel {
    override fun equalsContent(obj: UiModel): Boolean = when (obj) {
        is CastModel -> results == obj.results
        else -> false
    }
}

data class RatingPromoModel(
    val title: String,
    val description: String,
    val ctaText: String
) : UiModel {
    override fun equalsContent(obj: UiModel): Boolean = when (obj) {
        is RatingPromoModel -> title == obj.title && description == obj.description
                && ctaText == obj.ctaText

        else -> false
    }
}

data class ReviewsModel(
    val results: List<Review>
) : UiModel {
    override fun equalsContent(obj: UiModel): Boolean = when (obj) {
        is ReviewsModel -> results == obj.results
        else -> false
    }
}

data class SimilarModel(
    val results: List<TvSeries>
) : UiModel {
    override fun equalsContent(obj: UiModel): Boolean = when (obj) {
        is SimilarModel -> results == obj.results
        else -> false
    }
}

data class RecommendationModel(
    val results: List<TvSeries>
) : UiModel {
    override fun equalsContent(obj: UiModel): Boolean = when (obj) {
        is RecommendationModel -> results == obj.results
        else -> false
    }
}