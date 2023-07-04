package com.stathis.data.mappers

import com.stathis.core.ext.toNotNull
import com.stathis.data.model.AnalyticsResponseDto
import com.stathis.domain.model.analytics.AnalyticsResponse

object AnalyticsMapper : BaseMapper<AnalyticsResponseDto?, AnalyticsResponse> {

    override fun toDomainModel(dto: AnalyticsResponseDto?) = AnalyticsResponse(
        ratings = dto?.ratings.toNotNull(),
        threads = dto?.threads.toNotNull(),
        users = dto?.users.toNotNull(),
        watchlistTopUser = UserMapper.toDomainModel(dto?.watchlistTopUser)
    )
}