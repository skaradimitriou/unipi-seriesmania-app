package com.stathis.data.model

import com.stathis.domain.model.forum.ForumThread

data class AnalyticsResponseDto(
    val ratings: Int? = null,
    val reviews: Int? = null,
    val users: Int? = null,
    val threads: Int? = null,
    val watchlistTopUser: UserDto? = null,
    val topRatingsUser: UserDto? = null,
    val userWithMostFollowers: UserDto? = null,
    val topForumContributor: UserDto? = null,
    val topFiveMostRatedSeries: List<TvSeriesDetailsDto>? = null,
    val discussionWithMostReplies: ForumThread? = null,
    val topPreferences: List<GenreDto>? = null
)