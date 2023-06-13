package com.stathis.seriesmania.ui.profile.profile

import android.content.Intent
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.stathis.core.adapters.profile.UserProfileAdapter
import com.stathis.core.adapters.profile.UserProfileCallback
import com.stathis.core.base.BaseFragment
import com.stathis.core.ext.askUserForAction
import com.stathis.core.ext.setScreenTitle
import com.stathis.core.util.MODE
import com.stathis.core.util.SERIES
import com.stathis.domain.model.TvSeries
import com.stathis.domain.model.profile.User
import com.stathis.seriesmania.R
import com.stathis.seriesmania.databinding.FragmentProfileBinding
import com.stathis.seriesmania.ui.onboarding.OnboardingActivity
import com.stathis.seriesmania.ui.profile.ProfileActivityViewModel
import com.stathis.seriesmania.ui.profile.navigator.ProfileAction
import com.stathis.seriesmania.ui.results.ResultsActivity
import com.stathis.seriesmania.ui.results.navigator.ResultAction
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile),
    UserProfileCallback {

    private val viewModel: ProfileViewModel by viewModels()
    private val activityViewModel: ProfileActivityViewModel by activityViewModels()

    private val adapter = UserProfileAdapter(this)

    override fun init() {
        setScreenTitle(getString(com.stathis.core.R.string.profile_title))
        binding.adapter = adapter
    }

    override fun startOps() {
        viewModel.getProfileInfo()

        viewModel.userInfo.observe(viewLifecycleOwner) { uiData ->
            adapter.submitList(uiData)
        }

        viewModel.userLoggedOut.observe(viewLifecycleOwner) { loggedOut ->
            startActivity(Intent(requireContext(), OnboardingActivity::class.java))
            requireActivity().finish()
        }
    }

    override fun stopOps() {}

    override fun onImageClick() {
        activityViewModel.navigateToScreen(ProfileAction.CHANGE_PROFILE_PHOTO)
    }

    override fun onFollowClick(model: User) {
        //handle follow mechanism
    }

    override fun onMyFollowingUsersClick() {
        activityViewModel.navigateToScreen(ProfileAction.FOLLOWING)
    }

    override fun onMyFollowsClick() {
        activityViewModel.navigateToScreen(ProfileAction.FOLLOWERS)
    }

    override fun onWatchlistClick() {
        activityViewModel.navigateToScreen(ProfileAction.WATCHLIST)
    }

    override fun onPreferencesClick() {
        //Go to preferences screen
        Timber.d("")
    }

    override fun onSeriesClick(model: TvSeries) {
        startActivity(Intent(requireContext(), ResultsActivity::class.java).apply {
            putExtra(MODE, ResultAction.DETAILS)
            putExtra(SERIES, model)
        })
    }

    override fun onLogoutClick() {
        askUserForAction(
            title = getString(com.stathis.core.R.string.ask_user_logout),
            btnTitle = getString(com.stathis.core.R.string.logout)
        ) {
            viewModel.logoutUser()
        }
    }
}