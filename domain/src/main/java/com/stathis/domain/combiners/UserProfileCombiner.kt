package com.stathis.domain.combiners

import com.stathis.domain.model.TvSeriesWrapper
import com.stathis.domain.model.UiModel
import com.stathis.domain.model.profile.OtherUser
import com.stathis.domain.model.profile.uimodel.EmptyUserPreferences
import com.stathis.domain.model.profile.uimodel.EmptyWatchlist
import com.stathis.domain.model.profile.uimodel.UserStatistics
import com.stathis.domain.usecases.follow.FetchMyFollowersUseCase
import com.stathis.domain.usecases.follow.FetchWhoFollowsMeUseCase
import com.stathis.domain.usecases.profile.FetchUserByIdUseCase
import com.stathis.domain.usecases.watchlist.FetchWatchlistUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class UserProfileCombiner @Inject constructor(
    private val fetchUserByIdUseCase: FetchUserByIdUseCase,
    private val watchlistUseCase: FetchWatchlistUseCase,
    private val followsUseCase: FetchMyFollowersUseCase,
    private val whoFollowsMeUseCase: FetchWhoFollowsMeUseCase
) : BaseCombiner<List<UiModel>> {

    override suspend fun invoke(vararg args: Any?): List<UiModel> = coroutineScope {
        val userId = args.getOrNull(0) as? String ?: ""

        val profile = async { fetchUserByIdUseCase.invoke(userId) }.await()

        var watchlist: TvSeriesWrapper? = null
        async {
            watchlistUseCase.invoke().collect {
                if (it.isNotEmpty()) watchlist = TvSeriesWrapper(it, "Watchlist")
            }
        }.await()

        val followList = mutableListOf<OtherUser>()
        async {
            followsUseCase.invoke().collect {
                if (it.isNotEmpty()) followList.addAll(it)
            }
        }.await()

        val whoFollowsMe = mutableListOf<OtherUser>()
        async {
            whoFollowsMeUseCase.invoke().collect {
                if (it.isNotEmpty()) whoFollowsMe.addAll(it)
            }
        }.await()

        val list = mutableListOf<UiModel>()

        list.add(profile)

        list.add(
            UserStatistics(
                following = followList.size.toString(),
                followers = whoFollowsMe.size.toString(),
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

        return@coroutineScope list
    }
}