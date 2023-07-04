package com.stathis.seriesmania.ui.settings.analytics

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.stathis.core.adapters.analytics.AnalyticsGenerator
import com.stathis.core.base.BaseViewModel
import com.stathis.domain.model.Result
import com.stathis.domain.model.UiModel
import com.stathis.domain.usecases.settings.FetchAppAnalyticsUseCase
import com.stathis.seriesmania.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    app: Application,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val fetchAppAnalyticsUseCase: FetchAppAnalyticsUseCase
) : BaseViewModel(app) {

    val analytics: LiveData<Result<List<UiModel>>>
        get() = _analytics

    private val _analytics = MutableLiveData<Result<List<UiModel>>>()

    fun fetchAppAnalytics() {
        _analytics.postValue(Result.Loading())
        viewModelScope.launch(dispatcher) {
            fetchAppAnalyticsUseCase.invoke().collect { response ->
                val data = AnalyticsGenerator.generate(response)
                _analytics.postValue(Result.Success(data))
            }
        }
    }
}