package com.stathis.core.adapters.analytics

import com.stathis.core.adapters.analytics.uimodel.AnalyticsDetail
import com.stathis.domain.model.UiModel
import com.stathis.domain.model.analytics.AnalyticsResponse

object AnalyticsGenerator {

    fun generate(data: AnalyticsResponse): List<UiModel> = listOf(
        AnalyticsDetail(
            title = "Total users",
            value = data.users.toString()
        ),
        AnalyticsDetail(
            title = "Total Ratings Made",
            value = data.ratings.toString()
        ),
        AnalyticsDetail(
            title = "Total Discussions",
            value = data.threads.toString()
        )
    )
}