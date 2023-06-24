package com.stathis.data.mappers

import com.stathis.core.ext.toNotNull
import com.stathis.data.model.TvSeriesDetailsDto
import com.stathis.domain.model.TvSeriesDetails

object TvSeriesDetailsMapper : BaseMapper<TvSeriesDetailsDto?, TvSeriesDetails> {

    override fun toDomainModel(dto: TvSeriesDetailsDto?) = TvSeriesDetails(
        backdropPath = dto?.backdrop_path.toNotNull(),
        firstAirDate = dto?.first_air_date.toNotNull(),
        genres = dto?.genres?.joinToString(", ") { it.name.toNotNull() }.toNotNull(),
        id = dto?.id.toNotNull(),
        name = dto?.name.toNotNull(),
        numberOfEpisodes = dto?.number_of_episodes.toNotNull(),
        numberOfSeasons = dto?.number_of_seasons.toNotNull(),
        overview = dto?.overview.toNotNull(),
        posterPath = dto?.poster_path.toNotNull(),
        voteAverage = dto?.vote_average.toNotNull(),
        voteCount = dto?.vote_count.toNotNull()
    )
}