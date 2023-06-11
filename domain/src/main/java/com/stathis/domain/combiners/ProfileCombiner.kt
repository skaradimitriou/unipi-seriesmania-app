package com.stathis.domain.combiners

import com.stathis.domain.model.TvSeriesWrapper
import com.stathis.domain.model.UiModel
import com.stathis.domain.model.profile.uimodel.EmptyUserPreferences
import com.stathis.domain.model.profile.uimodel.EmptyWatchlist
import com.stathis.domain.model.profile.uimodel.LogoutOption
import com.stathis.domain.model.profile.uimodel.UserStatistics
import com.stathis.domain.usecases.profile.GetProfileInfoUseCase
import com.stathis.domain.usecases.watchlist.FetchWatchlistUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class ProfileCombiner @Inject constructor(
    private val profileInfoUseCase: GetProfileInfoUseCase,
    private val watchlistUseCase: FetchWatchlistUseCase
) : BaseCombiner<List<UiModel>> {

    override suspend fun invoke(vararg args: Any?): List<UiModel> = coroutineScope {
        val profile = async { profileInfoUseCase.invoke() }.await()

        var watchlist: TvSeriesWrapper? = null
        async {
            watchlistUseCase.invoke().collect {
                if (it.isNotEmpty()) watchlist = TvSeriesWrapper(it)
            }
        }.await()

        val list = mutableListOf<UiModel>()

        list.add(profile)

        //FIXME: Calculate follows once it can be done and replace hardcoded values.

        list.add(
            UserStatistics(
                following = "25",
                followers = "26",
                watchlist = (watchlist?.series?.size ?: 0).toString()
            )
        )

        if (profile.preferences.isEmpty()) {
            list.add(EmptyUserPreferences())
        } else {
            //
        }

        watchlist?.let {
            list.add(it)
        } ?: kotlin.run {
            //user has no watchlist, add promo model
            list.add(EmptyWatchlist())
        }

        list.add(LogoutOption(buttonTxt = "Αποσύνδεση"))

        return@coroutineScope list
    }
}