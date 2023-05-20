package com.stathis.domain.repositories

import com.stathis.domain.model.cast.Cast

interface CastRepository {

    suspend fun getCastForSeries(seriesId: Int): List<Cast>
}