package com.stathis.seriesmania.ui.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stathis.seriesmania.ui.onboarding.navigator.OnboardingAction

class OnboardingSharedViewModel : ViewModel() {

    val navigatorState: LiveData<OnboardingAction?>
        get() = _navigatorState

    private val _navigatorState = MutableLiveData<OnboardingAction?>()

    fun resetNavigation() = _navigatorState.postValue(null)

    fun navigateToScreen(action: OnboardingAction?) {
        _navigatorState.postValue(action)
    }
}