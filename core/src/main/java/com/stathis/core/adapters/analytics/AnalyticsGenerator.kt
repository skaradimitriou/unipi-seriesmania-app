package com.stathis.core.adapters.analytics

import android.content.Context
import com.stathis.core.R
import com.stathis.core.adapters.analytics.uimodel.AnalyticsGeneralItem
import com.stathis.core.adapters.analytics.uimodel.AnalyticsPreferencesItem
import com.stathis.core.adapters.analytics.uimodel.AnalyticsSeriesItem
import com.stathis.core.adapters.analytics.uimodel.AnalyticsThreadItem
import com.stathis.core.adapters.analytics.uimodel.AnalyticsUsersChildItem
import com.stathis.core.adapters.analytics.uimodel.AnalyticsUsersItem
import com.stathis.domain.model.UiModel
import com.stathis.domain.model.analytics.AnalyticsResponse

object AnalyticsGenerator {

    fun generate(data: AnalyticsResponse, context: Context): List<UiModel> = listOf(
        AnalyticsGeneralItem(
            howManyUsers = data.users.toString(),
            howManyForumDiscussions = data.threads.toString(),
            howManyRatingMade = data.ratings.toString(),
            howManyReviewsMade = data.ratings.toString()
        ),
        AnalyticsSeriesItem(
            series = data.topFiveMostRatedSeries
        ),
        AnalyticsUsersItem(
            listOf(
                AnalyticsUsersChildItem(
                    title = context.getString(R.string.most_ratings_made_by),
                    user = data.topRatingsUser
                ),
                AnalyticsUsersChildItem(
                    title = context.getString(R.string.user_biggest_watchlist),
                    user = data.watchlistTopUser
                ),
                AnalyticsUsersChildItem(
                    title = context.getString(R.string.user_most_followers),
                    user = data.userWithMostFollowers
                ),
                AnalyticsUsersChildItem(
                    title = context.getString(R.string.most_thread_replies_user),
                    user = data.topForumContributor
                )
            )
        ),
        AnalyticsThreadItem(
            thread = data.discussionWithMostReplies
        ),
        AnalyticsPreferencesItem(
            string = data.topPreferences
        )
    )
}