package com.stathis.data.model

data class ReviewsFeedDto(
    val id: Int? = null,
    val page: Int? = null,
    val results: List<ReviewDto>? = null,
    val total_pages: Int? = null,
    val total_results: Int? = null
)
