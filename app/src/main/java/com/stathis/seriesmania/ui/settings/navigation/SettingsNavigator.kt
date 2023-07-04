package com.stathis.seriesmania.ui.settings.navigation

interface SettingsNavigator {

    fun navigateTo(screenKey: SettingsAction)
    fun goBack()
}