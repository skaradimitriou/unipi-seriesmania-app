package com.stathis.seriesmania.ui.onboarding.navigator

interface Navigator {

    fun navigateTo(screenKey: OnboardingAction)
    fun goBack()
}