package com.stathis.domain.usecases.watchlist

import com.stathis.domain.model.TvSeries
import com.stathis.domain.repositories.WatchlistRepository
import com.stathis.domain.usecases.BaseUseCase
import javax.inject.Inject

class AddToWatchlistUseCase @Inject constructor(
    private val repo: WatchlistRepository
) : BaseUseCase<Unit> {

    override suspend fun invoke(vararg args: Any?) {
        val item = args.getOrNull(0) as TvSeries
        return repo.addNewItem(item)
    }
}