package com.stathis.seriesmania.ui.results

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stathis.seriesmania.ui.results.navigator.ResultAction

class ResultsActivityViewModel : ViewModel() {

    val navigatorState: LiveData<ResultAction?>
        get() = _navigatorState

    private val _navigatorState = MutableLiveData<ResultAction?>()

    fun resetNavigation() = _navigatorState.postValue(null)

    fun navigateToScreen(action: ResultAction?) {
        _navigatorState.postValue(action)
    }
}