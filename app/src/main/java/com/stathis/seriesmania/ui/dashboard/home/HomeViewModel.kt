package com.stathis.seriesmania.ui.dashboard.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.stathis.core.base.BaseViewModel
import com.stathis.domain.combiners.DashboardDataCombiner
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
    private val homeCombiner: DashboardDataCombiner
) : BaseViewModel(app) {

    val dashboardData: LiveData<List<UiModel>>
        get() = _dashboardData

    private val _dashboardData = MutableLiveData<List<UiModel>>()

    fun getData() {
        viewModelScope.launch(dispatcher) {
            val result = homeCombiner.invoke()
            val list = listOf(
                result.profileInfo,
                result.popularSeries,
                result.topRatedSeries,
                result.trendingSeries,
                result.airingTodaySeries
            )
            _dashboardData.postValue(list)
        }
    }
}