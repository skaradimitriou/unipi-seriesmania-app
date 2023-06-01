package com.stathis.data.mappers

import com.stathis.core.ext.toNotNull
import com.stathis.data.model.ReviewDto
import com.stathis.data.model.ReviewsFeedDto
import com.stathis.domain.model.reviews.Review

object ReviewsMapper : BaseMapper<ReviewsFeedDto?, List<Review>> {

    override suspend fun toDomainModel(dto: ReviewsFeedDto?): List<Review> {
        return dto?.results.toDomainModel()
    }

    private fun List<ReviewDto>?.toDomainModel() = this?.map {
        it.toDomainModel()
    } ?: listOf()

    private fun ReviewDto?.toDomainModel() = Review(
        id = this?.id.toNotNull(),
        author = this?.author.toNotNull(),
        content = this?.content.toNotNull(),
        url = this?.url.toNotNull()
    )
}