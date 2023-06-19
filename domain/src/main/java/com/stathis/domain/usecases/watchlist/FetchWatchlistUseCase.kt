package com.stathis.domain.usecases.watchlist

import com.stathis.domain.model.TvSeries
import com.stathis.domain.repositories.WatchlistRepository
import com.stathis.domain.usecases.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchWatchlistUseCase @Inject constructor(
    private val repo: WatchlistRepository
) : BaseUseCase<Flow<List<TvSeries>>> {

    override suspend fun invoke(vararg args: Any?): Flow<List<TvSeries>> {
        val userId = args.getOrNull(0) as? String ?: ""
        return if (userId.isEmpty()) {
            repo.getAllItems()
        } else {
            repo.getAllItemsByUserId(userId)
        }
    }
}