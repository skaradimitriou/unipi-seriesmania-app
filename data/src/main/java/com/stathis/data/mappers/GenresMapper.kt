package com.stathis.data.mappers

import com.stathis.core.ext.toNotNull
import com.stathis.data.model.GenreDto
import com.stathis.data.model.GenresFeedDto
import com.stathis.domain.model.genres.Genre

object GenresMapper : BaseMapper<GenresFeedDto?, List<Genre>> {

    override fun toDomainModel(dto: GenresFeedDto?): List<Genre> {
        return dto?.genres.toDomainModel()
    }

    private fun List<GenreDto>?.toDomainModel() = this?.map {
        it.toDomainModel()
    } ?: listOf()

    private fun GenreDto?.toDomainModel() = Genre(
        id = this?.id.toNotNull(),
        name = this?.name.toNotNull()
    )
}