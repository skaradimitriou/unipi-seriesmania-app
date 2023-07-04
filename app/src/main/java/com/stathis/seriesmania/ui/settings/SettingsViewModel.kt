package com.stathis.seriesmania.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stathis.seriesmania.ui.settings.navigation.SettingsAction

class SettingsViewModel : ViewModel() {

    val navigatorState: LiveData<SettingsAction?>
        get() = _navigatorState

    private val _navigatorState = MutableLiveData<SettingsAction?>()

    fun resetNavigation() = _navigatorState.postValue(null)

    fun navigateToScreen(action: SettingsAction?) {
        _navigatorState.postValue(action)
    }
}