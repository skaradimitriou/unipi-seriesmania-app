package com.stathis.domain.model

data class TvSeriesFeed(
    val page: Int,
    val total_pages: Int,
    val total_results: Int,
    val results: List<TvSeries>
)