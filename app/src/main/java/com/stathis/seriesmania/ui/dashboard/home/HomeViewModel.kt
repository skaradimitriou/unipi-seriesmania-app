package com.stathis.seriesmania.ui.dashboard.home

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.stathis.domain.usecases.dashboard.GetPopularSeriesUseCase
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
    private val popularSeriesUseCase: GetPopularSeriesUseCase
) : BaseViewModel(app) {

    fun getData() {
        viewModelScope.launch(dispatcher) {
            val result = popularSeriesUseCase.invoke()
            Timber.d("RESULTS => $result")
        }
    }
}