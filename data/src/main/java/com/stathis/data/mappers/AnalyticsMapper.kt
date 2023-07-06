package com.stathis.data.mappers

import com.stathis.core.ext.toNotNull
import com.stathis.data.model.AnalyticsResponseDto
import com.stathis.data.model.TvSeriesDetailsDto
import com.stathis.domain.model.analytics.AnalyticsResponse
import com.stathis.domain.model.forum.ForumThread

object AnalyticsMapper : BaseMapper<AnalyticsResponseDto?, AnalyticsResponse> {

    override fun toDomainModel(dto: AnalyticsResponseDto?) = AnalyticsResponse(
        ratings = dto?.ratings.toNotNull(),
        reviews = dto?.reviews.toNotNull(),
        threads = dto?.threads.toNotNull(),
        users = dto?.users.toNotNull(),
        watchlistTopUser = UserMapper.toDomainModel(dto?.watchlistTopUser),
        topRatingsUser = UserMapper.toDomainModel(dto?.topRatingsUser),
        userWithMostFollowers = UserMapper.toDomainModel(dto?.userWithMostFollowers),
        topForumContributor = UserMapper.toDomainModel(dto?.topForumContributor),
        topFiveMostRatedSeries = dto?.topFiveMostRatedSeries.toDomainModel(),
        discussionWithMostReplies = dto?.discussionWithMostReplies ?: ForumThread(),
        topPreferences = dto?.topPreferences?.joinToString(", ") { it.name.toString() }.toNotNull()
    )

    private fun List<TvSeriesDetailsDto>?.toDomainModel() = this?.map {
        TvSeriesDetailsMapper.toDomainModel(it)
    } ?: listOf()
}