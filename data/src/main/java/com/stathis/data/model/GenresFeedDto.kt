package com.stathis.data.model

class GenresFeedDto(
    val genres: List<GenreDto>? = null
)

data class GenreDto(
    val id: Int? = null,
    val name: String? = null
)