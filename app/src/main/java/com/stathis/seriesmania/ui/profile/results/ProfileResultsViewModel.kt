package com.stathis.seriesmania.ui.profile.results

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.stathis.core.base.BaseViewModel
import com.stathis.core.util.auth.Authenticator
import com.stathis.domain.model.Result
import com.stathis.domain.model.UiModel
import com.stathis.domain.model.profile.uimodel.EmptyFollowers
import com.stathis.domain.model.profile.uimodel.EmptyFollowing
import com.stathis.domain.model.profile.uimodel.EmptyWatchlist
import com.stathis.domain.usecases.follow.FetchMyFollowersDetailsUseCase
import com.stathis.domain.usecases.follow.FetchWhoFollowsMeUseCase
import com.stathis.domain.usecases.watchlist.FetchWatchlistUseCase
import com.stathis.seriesmania.di.IoDispatcher
import com.stathis.seriesmania.ui.profile.results.type.ProfileResultsType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileResultsViewModel @Inject constructor(
    app: Application,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val fetchMyFollowsUseCase: FetchMyFollowersDetailsUseCase,
    private val fetchWhoFollowsMeUseCase: FetchWhoFollowsMeUseCase,
    private val fetchWatchlistUseCase: FetchWatchlistUseCase,
    private val auth: Authenticator
) : BaseViewModel(app) {

    val follows: LiveData<Result<List<UiModel>>>
        get() = _follows

    private val _follows = MutableLiveData<Result<List<UiModel>>>()

    val watchlist: LiveData<Result<List<UiModel>>>
        get() = _watchlist

    private val _watchlist = MutableLiveData<Result<List<UiModel>>>()

    fun getResults(
        type: ProfileResultsType,
        userId: String? = auth.getActiveUserId()
    ) = when (type) {
        ProfileResultsType.FOLLOWING -> getMyFollowingUsers(userId)
        ProfileResultsType.FOLLOWERS -> getWhoFollowsMe(userId)
        ProfileResultsType.WATCHLIST -> getMyWatchlist(userId)
    }

    private fun getMyFollowingUsers(userId: String?) {
        _follows.postValue(Result.Loading())
        viewModelScope.launch(dispatcher) {
            fetchMyFollowsUseCase.invoke(userId).collect {
                val data = it.ifEmpty { listOf(EmptyFollowing()) }
                _follows.postValue(Result.Success(data))
            }
        }
    }

    private fun getWhoFollowsMe(userId: String?) {
        _follows.postValue(Result.Loading())
        viewModelScope.launch(dispatcher) {
            fetchWhoFollowsMeUseCase.invoke(userId).collect {
                val data = it.ifEmpty { listOf(EmptyFollowers()) }
                _follows.postValue(Result.Success(data))
            }
        }
    }

    private fun getMyWatchlist(userId: String?) {
        _watchlist.postValue(Result.Loading())
        viewModelScope.launch(dispatcher) {
            fetchWatchlistUseCase.invoke(userId).collect {
                val data = it.ifEmpty { listOf(EmptyWatchlist()) }
                _watchlist.postValue(Result.Success(data))
            }
        }
    }
}