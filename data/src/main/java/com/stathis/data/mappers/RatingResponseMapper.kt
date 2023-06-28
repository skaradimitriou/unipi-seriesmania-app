package com.stathis.data.mappers

import com.stathis.core.ext.toNotNull
import com.stathis.data.model.RatingResponseDto

object RatingResponseMapper : BaseMapper<RatingResponseDto?, RatingResponseDto> {

    override fun toDomainModel(dto: RatingResponseDto?) = RatingResponseDto(
        success = dto?.success.toNotNull(),
        status_code = dto?.status_code.toNotNull(),
        status_message = dto?.status_message.toNotNull(),
    )
}