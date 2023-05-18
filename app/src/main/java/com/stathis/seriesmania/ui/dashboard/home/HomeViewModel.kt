package com.stathis.seriesmania.ui.dashboard.home

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.stathis.domain.combiners.DashboardDataCombiner
import com.stathis.seriesmania.base.BaseViewModel
import com.stathis.seriesmania.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    app: Application,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val homeCombiner: DashboardDataCombiner
) : BaseViewModel(app) {

    fun getData() {
        viewModelScope.launch(dispatcher) {
            val result = homeCombiner.invoke()
            Timber.d("RESULTS => $result")
        }
    }
}