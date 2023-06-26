package com.stathis.seriesmania.ui.dashboard.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.stathis.core.base.BaseViewModel
import com.stathis.core.util.session.SessionManager
import com.stathis.domain.combiners.DashboardDataCombiner
import com.stathis.domain.model.Result
import com.stathis.domain.model.UiModel
import com.stathis.seriesmania.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    app: Application,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val homeCombiner: DashboardDataCombiner,
    private val sessionManager: SessionManager
) : BaseViewModel(app) {

    val dashboardData: LiveData<Result<List<UiModel>>>
        get() = _dashboardData

    private val _dashboardData = MutableLiveData<Result<List<UiModel>>>()

    fun getData() {
        _dashboardData.postValue(Result.Loading())
        viewModelScope.launch(dispatcher) {
            val result = homeCombiner.invoke()
            val list = mutableListOf(
                result.profileInfo,
                result.popularSeries,
                result.topRatedSeries,
                result.trendingSeries,
                result.airingTodaySeries,
            )

            result.watchlist?.let { list.add(it) }
            result.firstPreference?.let { list.add(it) }
            result.secondPreference?.let { list.add(it) }
            result.thirdPreference?.let { list.add(it) }
            _dashboardData.postValue(Result.Success(list))
        }
    }
}