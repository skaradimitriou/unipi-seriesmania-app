package com.stathis.seriesmania.ui.settings.navigation

import android.app.Activity
import androidx.navigation.NavController
import com.stathis.seriesmania.R
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject


@ActivityScoped
class SettingsNavigatorImpl @Inject constructor(
    private val activity: Activity?,
    private val navController: NavController
) : SettingsNavigator {

    override fun navigateTo(screenKey: SettingsAction) = when (screenKey) {
        SettingsAction.INTRO -> navController.navigate(R.id.settingsIntroFragment)
        SettingsAction.ANALYTICS -> navController.navigate(R.id.analyticsFragment)
        SettingsAction.FAQ -> navController.navigate(R.id.faqFragment)
        SettingsAction.ABOUT -> navController.navigate(R.id.aboutFragment)
    }

    override fun goBack() {
        if (navController.graph.startDestinationId == navController.currentDestination?.id) {
            activity?.finish()
        } else {
            navController.navigateUp()
        }
    }
}