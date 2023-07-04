package com.stathis.data.mappers

import com.stathis.core.ext.toListOf
import com.stathis.core.ext.toNotNull
import com.stathis.core.util.GenresGenerator
import com.stathis.domain.model.TvSeries
import com.stathis.domain.model.TvSeriesWrapper

object WatchlistMapper : BaseMapper<TvSeriesWrapper?, List<TvSeries>> {

    override fun toDomainModel(dto: TvSeriesWrapper?): List<TvSeries> {
        return dto?.series.toListOf {
            TvSeries(
                original_name = it.original_name.toNotNull(),
                genre_ids = it.genre_ids.toNotNull(),
                name = it.name.toNotNull(),
                popularity = it.popularity.toNotNull(),
                origin_country = it.origin_country.toNotNull(),
                vote_count = it.vote_count.toNotNull(),
                first_air_date = it.first_air_date.toNotNull(),
                backdrop_path = it.backdrop_path.toNotNull(),
                original_language = it.original_language.toNotNull(),
                id = it.id.toNotNull(),
                vote_average = it.vote_average.toNotNull(),
                overview = it.overview.toNotNull(),
                poster_path = it.poster_path.toNotNull(),
                genres = GenresGenerator.getGenre(
                    it.genre_ids.getOrNull(0).toNotNull()
                )?.name.toNotNull()
            )
        }
    }
}