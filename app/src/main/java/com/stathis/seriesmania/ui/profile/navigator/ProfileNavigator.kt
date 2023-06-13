package com.stathis.seriesmania.ui.profile.navigator

import android.os.Bundle

interface ProfileNavigator {

    fun init(mode: ProfileAction)
    fun navigateTo(screenKey: ProfileAction, args: Bundle? = null)
    fun goBack()
}