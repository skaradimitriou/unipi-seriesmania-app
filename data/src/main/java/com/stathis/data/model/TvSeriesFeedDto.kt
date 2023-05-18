package com.stathis.data.model

data class TvSeriesFeedDto(
    val page: Int? = null,
    val total_pages: Int? = null,
    val total_results: Int? = null,
    val results: List<TvSeriesDto>? = null
)