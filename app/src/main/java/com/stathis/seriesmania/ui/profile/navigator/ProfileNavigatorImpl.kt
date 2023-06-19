package com.stathis.seriesmania.ui.profile.navigator

import android.app.Activity
import android.os.Bundle
import androidx.navigation.NavController
import com.stathis.core.ext.init
import com.stathis.core.util.TYPE
import com.stathis.seriesmania.R
import com.stathis.seriesmania.ui.profile.results.type.ProfileResultsType
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class ProfileNavigatorImpl @Inject constructor(
    private val activity: Activity?, private val navController: NavController
) : ProfileNavigator {

    override fun init(mode: ProfileAction) {
        val destination = when (mode) {
            ProfileAction.USER_PROFILE -> R.id.userProfileFragment
            else -> R.id.profileFragment
        }
        navController.init(R.navigation.profile_nav, destination)
    }

    override fun navigateTo(screenKey: ProfileAction, args: Bundle?) {
        when (screenKey) {
            ProfileAction.USER_PROFILE -> navController.navigate(R.id.userProfileFragment, args)
            ProfileAction.MY_PROFILE -> navController.navigate(R.id.profileFragment, args)
            ProfileAction.CHANGE_PROFILE_PHOTO -> {
                navController.navigate(R.id.uploadImageFragment, args)
            }
            ProfileAction.PHOTO_UPLOADED -> goBack()
            ProfileAction.UPDATE_PROFILE_INFO -> {
                navController.navigate(R.id.updateInfoFragment, args)
            }
            ProfileAction.PROFILE_INFO_UPDATED -> goBack()
            ProfileAction.FOLLOWING -> {
                navController.navigate(R.id.profileResultsFragment, args = Bundle().apply {
                    putSerializable(TYPE, ProfileResultsType.FOLLOWING)
                })
            }
            ProfileAction.FOLLOWERS -> {
                navController.navigate(R.id.profileResultsFragment, args = Bundle().apply {
                    putSerializable(TYPE, ProfileResultsType.FOLLOWERS)
                })
            }
            ProfileAction.WATCHLIST -> {
                navController.navigate(R.id.profileResultsFragment, args = Bundle().apply {
                    putSerializable(TYPE, ProfileResultsType.WATCHLIST)
                })
            }
            ProfileAction.SET_PREFERENCES -> {
                navController.navigate(R.id.preferencesFragment)
            }
            ProfileAction.PREFERENCES_UPDATED -> goBack()
        }
    }

    override fun goBack() {
        if (navController.graph.startDestinationId == navController.currentDestination?.id) {
            activity?.finish()
        } else {
            navController.navigateUp()
        }
    }
}