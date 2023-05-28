package com.stathis.domain.repositories

import com.stathis.domain.model.TvSeries
import com.stathis.domain.model.cast.ActorDetails
import com.stathis.domain.model.cast.Cast

interface CastRepository {

    suspend fun getCastForSeries(seriesId: Int): List<Cast>

    suspend fun getSeriesForActor(actorId: Int): List<TvSeries>

    suspend fun getPersonDetails(actorId: Int): ActorDetails
}