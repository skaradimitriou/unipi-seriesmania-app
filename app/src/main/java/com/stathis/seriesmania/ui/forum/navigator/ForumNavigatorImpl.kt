package com.stathis.seriesmania.ui.forum.navigator

import android.app.Activity
import androidx.navigation.NavController
import com.stathis.core.ext.init
import com.stathis.seriesmania.R
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class ForumNavigatorImpl @Inject constructor(
    private val activity: Activity?,
    private val navController: NavController
) : ForumNavigator {

    override fun init(mode: ForumAction) {
        val destination = when (mode) {
            ForumAction.ADD_NEW -> R.id.addThreadFragment
            ForumAction.THREAD_DETAILS -> R.id.threadDetailsFragment
        }
        navController.init(R.navigation.forum_nav, destination)
    }

    override fun navigateTo(screenKey: ForumAction) = when (screenKey) {
        ForumAction.ADD_NEW -> navController.navigate(R.id.addThreadFragment)
        ForumAction.THREAD_DETAILS -> navController.navigate(R.id.threadDetailsFragment)
    }

    override fun goBack() {
        if (navController.graph.startDestinationId == navController.currentDestination?.id) {
            activity?.finish()
        } else {
            navController.navigateUp()
        }
    }
}