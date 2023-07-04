package com.stathis.data.mappers

import com.stathis.core.ext.toNotNull
import com.stathis.data.model.FaqDto
import com.stathis.domain.model.faq.Faq

object FaqMapper : BaseMapper<List<FaqDto>?, List<Faq>> {

    override fun toDomainModel(dto: List<FaqDto>?) = dto?.map {
        it.toDomainModel()
    } ?: listOf()

    private fun FaqDto?.toDomainModel() = Faq(
        title = this?.title.toNotNull(),
        description = this?.description.toNotNull(),
        seq = this?.seq.toNotNull()
    )
}