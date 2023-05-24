package com.stathis.seriesmania.ui.results.navigator

import android.app.Activity
import androidx.navigation.NavController
import com.stathis.core.ext.init
import com.stathis.seriesmania.R
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class ResultNavigatorImpl @Inject constructor(
    private val activity: Activity?,
    private val navController: NavController
) : ResultNavigator {

    override fun init(mode: ResultAction) {
        val destination = when (mode) {
            ResultAction.DETAILS -> R.id.detailsFragment
            ResultAction.RESULTS -> R.id.resultsFragment
        }
        navController.init(R.navigation.results_nav, destination)
    }

    override fun navigateTo(screenKey: ResultAction) = when (screenKey) {
        ResultAction.DETAILS -> navController.navigate(R.id.detailsFragment)
        ResultAction.RESULTS -> navController.navigate(R.id.resultsFragment)
    }

    override fun goBack() {
        if (navController.graph.startDestinationId == navController.currentDestination?.id) {
            activity?.finish()
        } else {
            navController.navigateUp()
        }
    }
}