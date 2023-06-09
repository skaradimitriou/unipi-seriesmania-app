package com.stathis.domain.repositories

import com.stathis.domain.model.TvSeries
import kotlinx.coroutines.flow.Flow

interface WatchlistRepository {

    suspend fun addNewItem(item: TvSeries)

    suspend fun removeItem(item: TvSeries)

    suspend fun getAllItems(): Flow<List<TvSeries>>
}