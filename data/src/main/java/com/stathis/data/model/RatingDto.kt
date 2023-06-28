package com.stathis.data.model

data class RatingDto(
    val userId: String? = null,
    val seriesId: String? = null,
    val value: Double? = null
) {
    constructor() : this("", "", 0.0)
}