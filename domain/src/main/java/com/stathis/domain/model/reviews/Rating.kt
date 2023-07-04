package com.stathis.domain.model.reviews

data class Rating(
    val userId: String,
    val username: String,
    val seriesId: String,
    val value: Double,
    val review: String
)