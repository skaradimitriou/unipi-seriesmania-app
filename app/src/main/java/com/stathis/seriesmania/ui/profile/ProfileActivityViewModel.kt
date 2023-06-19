package com.stathis.seriesmania.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stathis.seriesmania.ui.profile.navigator.ProfileAction

class ProfileActivityViewModel : ViewModel() {

    val navigatorState: LiveData<ProfileAction?>
        get() = _navigatorState

    private val _navigatorState = MutableLiveData<ProfileAction?>()

    fun resetNavigation() = _navigatorState.postValue(null)

    fun navigateToScreen(action: ProfileAction?) {
        _navigatorState.postValue(action)
    }
}