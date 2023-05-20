package com.stathis.data.mappers

import com.stathis.core.ext.toNotNull
import com.stathis.data.model.CastDto
import com.stathis.data.model.CastFeedDto
import com.stathis.domain.model.cast.Cast

object CastMapper : BaseMapper<CastFeedDto?, List<Cast>> {

    override fun toDomainModel(dto: CastFeedDto?): List<Cast> {
        return dto?.cast.toDomainModel()
    }

    private fun List<CastDto?>?.toDomainModel(): List<Cast> = this?.map {
        it.toDomainModel()
    } ?: listOf()

    private fun CastDto?.toDomainModel(): Cast = Cast(
        cast_id = this?.cast_id.toNotNull(),
        character = this?.character.toNotNull(),
        credit_id = this?.credit_id.toNotNull(),
        id = this?.id.toNotNull(),
        name = this?.name.toNotNull(),
        order = this?.order.toNotNull(),
        profile_path = this?.profile_path.toNotNull()
    )
}