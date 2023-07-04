package com.stathis.core.util

import com.stathis.domain.model.genres.Genre

object GenresGenerator {

    val genres = listOf(
        Genre(
            id = 10759,
            name = "Action & Adventure"
        ),
        Genre(
            id = 16,
            name = "Animation"
        ),
        Genre(
            id = 35,
            name = "Comedy"
        ),
        Genre(
            id = 80,
            name = "Crime"
        ),
        Genre(
            id = 99,
            name = "Documentary"
        ),
        Genre(
            id = 18,
            name = "Drama"
        ),
        Genre(
            id = 10751,
            name = "Family"
        ),
        Genre(
            id = 10762,
            name = "Kids"
        ),
        Genre(
            id = 9648,
            name = "Mystery"
        ),
        Genre(
            id = 10763,
            name = "News"
        ),
        Genre(
            id = 10764,
            name = "Reality"
        ),
        Genre(
            id = 10765,
            name = "Sci-Fi & Fantasy"
        ),
        Genre(
            id = 10766,
            name = "Soap"
        ),
        Genre(
            id = 10767,
            name = "Talk"
        ),
        Genre(
            id = 10768,
            name = "War & Politics"
        ),
        Genre(
            id = 37,
            name = "Western"
        )
    )

    fun getGenre(id: Int) = genres.find { it.id == id } ?: Genre(0, "Genre Not Available")
}