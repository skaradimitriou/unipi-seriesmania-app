package com.stathis.core.adapters.analytics.uimodel

import com.stathis.domain.model.UiModel

data class AnalyticsGeneralItem(
    val howManyUsers: String,
    val howManyForumDiscussions: String,
    val howManyRatingMade: String,
    val howManyReviewsMade: String
) : UiModel {
    override fun equalsContent(obj: UiModel) = when (obj) {
        is AnalyticsGeneralItem -> {
            howManyUsers == obj.howManyUsers && howManyForumDiscussions == obj.howManyForumDiscussions
                    && howManyRatingMade == obj.howManyRatingMade && howManyReviewsMade == obj.howManyReviewsMade
        }

        else -> false
    }
}