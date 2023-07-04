package com.stathis.domain.model.analytics

import com.stathis.domain.model.profile.User

data class AnalyticsResponse(
    val ratings: Int,
    val users: Int,
    val threads: Int,
    val watchlistTopUser: User
)