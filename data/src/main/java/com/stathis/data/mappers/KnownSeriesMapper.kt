package com.stathis.data.mappers

import com.stathis.data.mappers.TvSeriesMapper.toDomainModel
import com.stathis.data.model.KnownSeriesFeedDto
import com.stathis.domain.model.TvSeries

object KnownSeriesMapper : BaseMapper<KnownSeriesFeedDto?, List<TvSeries>> {

    override fun toDomainModel(dto: KnownSeriesFeedDto?): List<TvSeries> {
        return dto?.cast.toDomainModel()
    }
}