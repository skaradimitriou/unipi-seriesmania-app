package com.stathis.domain.model.details

import com.stathis.domain.model.TvSeries
import com.stathis.domain.model.UiModel
import com.stathis.domain.model.cast.Cast
import com.stathis.domain.model.reviews.Review

data class DetailsUiModel(
    val cast: CastModel,
    val reviews: ReviewsModel,
    val similar: SimilarModel,
    val recommendations: RecommendationModel,
) : UiModel {
    override fun equalsContent(obj: UiModel): Boolean = false
}

data class CastModel(
    val results: List<Cast>
) : UiModel {
    override fun equalsContent(obj: UiModel): Boolean = false
}

data class ReviewsModel(
    val results: List<Review>
) : UiModel {
    override fun equalsContent(obj: UiModel): Boolean = false
}

data class SimilarModel(
    val results: List<TvSeries>
) : UiModel {
    override fun equalsContent(obj: UiModel): Boolean = false
}

data class RecommendationModel(
    val results: List<TvSeries>
) : UiModel {
    override fun equalsContent(obj: UiModel): Boolean = false
}