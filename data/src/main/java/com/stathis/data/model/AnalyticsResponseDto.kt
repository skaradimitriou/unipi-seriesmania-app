package com.stathis.data.model

data class AnalyticsResponseDto(
    val ratings: Int? = null,
    val users: Int? = null,
    val threads: Int? = null,
    val watchlistTopUser: UserDto? = null
)