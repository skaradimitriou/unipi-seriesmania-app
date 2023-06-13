package com.stathis.seriesmania.ui.profile.results

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.stathis.core.base.BaseViewModel
import com.stathis.domain.model.TvSeries
import com.stathis.domain.model.profile.OtherUser
import com.stathis.domain.usecases.follow.FetchMyFollowersUseCase
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
    private val fetchMyFollowsUseCase: FetchMyFollowersUseCase,
    private val fetchWhoFollowsMeUseCase: FetchWhoFollowsMeUseCase,
    private val fetchWatchlistUseCase: FetchWatchlistUseCase
) : BaseViewModel(app) {

    val follows: LiveData<List<OtherUser>>
        get() = _follows

    private val _follows = MutableLiveData<List<OtherUser>>()

    val watchlist: LiveData<List<TvSeries>>
        get() = _watchlist

    private val _watchlist = MutableLiveData<List<TvSeries>>()

    fun getResults(type: ProfileResultsType) = when (type) {
        ProfileResultsType.FOLLOWING -> getMyFollowingUsers()
        ProfileResultsType.FOLLOWERS -> getWhoFollowsMe()
        ProfileResultsType.WATCHLIST -> getMyWatchlist()
    }

    private fun getMyFollowingUsers() {
        viewModelScope.launch(dispatcher) {
            fetchMyFollowsUseCase.invoke().collect {
                _follows.postValue(it)
            }
        }
    }

    private fun getWhoFollowsMe() {
        viewModelScope.launch(dispatcher) {
            fetchWhoFollowsMeUseCase.invoke().collect {
                _follows.postValue(it)
            }
        }
    }

    private fun getMyWatchlist() {
        viewModelScope.launch(dispatcher) {
            fetchWatchlistUseCase.invoke().collect {
                _watchlist.postValue(it)
            }
        }
    }
}