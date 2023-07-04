package com.stathis.domain.combiners

import com.stathis.domain.model.Result
import com.stathis.domain.model.TvSeriesWrapper
import com.stathis.domain.model.UiModel
import com.stathis.domain.model.profile.uimodel.EmptyUserPreferences
import com.stathis.domain.model.profile.uimodel.EmptyWatchlist
import com.stathis.domain.model.profile.uimodel.LogoutOption
import com.stathis.domain.model.profile.uimodel.UserPreferences
import com.stathis.domain.model.profile.uimodel.UserStatistics
import com.stathis.domain.usecases.follow.FetchHowManyFollowersUseCase
import com.stathis.domain.usecases.follow.FetchHowManyFollowingUsersUseCase
import com.stathis.domain.usecases.profile.GetProfileInfoUseCase
import com.stathis.domain.usecases.watchlist.FetchWatchlistUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class ProfileCombiner @Inject constructor(
    private val profileInfoUseCase: GetProfileInfoUseCase,
    private val watchlistUseCase: FetchWatchlistUseCase,
    private val followingUsersUseCase: FetchHowManyFollowingUsersUseCase,
    private val howManyFollowers: FetchHowManyFollowersUseCase
) : BaseCombiner<Result<List<UiModel>>> {

    override suspend fun invoke(vararg args: Any?): Result<List<UiModel>> = coroutineScope {
        val profile = async { profileInfoUseCase.invoke() }.await()

        var watchlist: TvSeriesWrapper? = null
        async {
            watchlistUseCase.invoke().collect {
                if (it.isNotEmpty()) watchlist = TvSeriesWrapper(it, "Watchlist")
            }
        }.await()

        var following = 0
        async {
            followingUsersUseCase.invoke().collect {
                following += it
            }
        }.await()

        var followers = 0
        async {
            howManyFollowers.invoke().collect {
                followers += it
            }
        }.await()

        val list = mutableListOf<UiModel>()

        list.add(profile)

        list.add(
            UserStatistics(
                following = following.toString(),
                followers = followers.toString(),
                watchlist = (watchlist?.series?.size ?: 0).toString()
            )
        )

        if (profile.preferences.isEmpty()) {
            list.add(EmptyUserPreferences())
        } else {
            list.add(UserPreferences(prefs = profile.preferences))
        }

        watchlist?.let {
            list.add(it)
        } ?: kotlin.run {
            //user has no watchlist, add promo model
            list.add(EmptyWatchlist())
        }

        list.add(LogoutOption(buttonTxt = "Αποσύνδεση"))

        return@coroutineScope Result.Success(list)
    }
}