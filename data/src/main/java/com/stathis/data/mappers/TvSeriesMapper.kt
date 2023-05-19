package com.stathis.data.mappers

import com.stathis.core.ext.toNotNull
import com.stathis.data.model.TvSeriesDto
import com.stathis.data.model.TvSeriesFeedDto
import com.stathis.domain.model.TvSeries
import com.stathis.domain.model.TvSeriesFeed

object TvSeriesMapper : BaseMapper<TvSeriesFeedDto?, TvSeriesFeed> {

    override fun toDomainModel(dto: TvSeriesFeedDto?): TvSeriesFeed = TvSeriesFeed(
        page = dto?.page.toNotNull(),
        total_pages = dto?.total_pages.toNotNull(),
        total_results = dto?.total_results.toNotNull(),
        results = dto?.results.toDomainModel()
    )

    private fun List<TvSeriesDto>?.toDomainModel() = this?.map { dto ->
        dto.toDomainModel()
    } ?: listOf()

    private fun TvSeriesDto?.toDomainModel(): TvSeries = TvSeries(
        original_name = this?.original_name.toNotNull(),
        genre_ids = this?.genre_ids.toNotNull(),
        name = this?.name.toNotNull(),
        popularity = this?.popularity.toNotNull(),
        origin_country = this?.origin_country.toNotNull(),
        vote_count = this?.vote_count.toNotNull(),
        first_air_date = this?.first_air_date.toNotNull(),
        backdrop_path = this?.backdrop_path.toNotNull(),
        original_language = this?.original_language.toNotNull(),
        id = this?.id.toNotNull(),
        vote_average = this?.vote_average.toNotNull(),
        overview = this?.overview.toNotNull(),
        poster_path = this?.poster_path.toNotNull(),
    )
}