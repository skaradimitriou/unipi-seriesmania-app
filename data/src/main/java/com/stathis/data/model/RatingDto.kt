package com.stathis.data.model

data class RatingDto(
    val userId: String? = null,
    val username: String? = null,
    val seriesId: String? = null,
    val value: Double? = null,
    val review: String? = null
) {
    constructor() : this("", "", "", 0.0, "")
}