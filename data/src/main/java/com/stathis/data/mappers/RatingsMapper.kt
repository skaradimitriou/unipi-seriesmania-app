package com.stathis.data.mappers

import com.stathis.core.ext.toListOf
import com.stathis.core.ext.toNotNull
import com.stathis.data.model.RatingDto
import com.stathis.domain.model.reviews.Rating

object RatingsMapper : BaseMapper<List<RatingDto>?, List<Rating>> {

    override fun toDomainModel(dto: List<RatingDto>?) = dto?.toListOf {
        Rating(
            userId = it.userId.toNotNull(),
            seriesId = it.seriesId.toNotNull(),
            value = it.value.toNotNull()
        )
    } ?: listOf()
}
