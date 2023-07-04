package com.stathis.domain.model.reviews

data class Rating(
    val userId: String,
    val seriesId: String,
    val value: Double
)