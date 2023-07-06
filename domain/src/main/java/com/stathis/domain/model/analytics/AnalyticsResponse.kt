package com.stathis.domain.model.analytics

import com.stathis.domain.model.TvSeriesDetails
import com.stathis.domain.model.forum.ForumThread
import com.stathis.domain.model.profile.User

data class AnalyticsResponse(
    val ratings: Int,
    val users: Int,
    val threads: Int,
    val reviews: Int,
    val watchlistTopUser: User,
    val topRatingsUser: User,
    val userWithMostFollowers: User,
    val topForumContributor: User,
    val topFiveMostRatedSeries: List<TvSeriesDetails>,
    val discussionWithMostReplies: ForumThread,
    val topPreferences: String
)