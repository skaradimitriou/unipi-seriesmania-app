package com.stathis.domain.model.details

import com.stathis.domain.model.UiModel
import com.stathis.domain.model.cast.Cast
import com.stathis.domain.model.reviews.Review

data class DetailsUiModel(
    val cast: CastModel,
    val reviews: ReviewsModel
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