package com.stathis.seriesmania.ui.profile.otheruser

import android.content.Intent
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.stathis.core.adapters.profile.OtherUserProfileAdapter
import com.stathis.core.adapters.profile.OtherUserProfileCallback
import com.stathis.core.base.BaseFragment
import com.stathis.core.ext.getParcelable
import com.stathis.core.ext.hideLoader
import com.stathis.core.ext.setScreenTitle
import com.stathis.core.ext.showLoader
import com.stathis.core.util.MODE
import com.stathis.core.util.SERIES
import com.stathis.core.util.USER
import com.stathis.domain.model.Result
import com.stathis.domain.model.TvSeries
import com.stathis.domain.model.profile.User
import com.stathis.seriesmania.R
import com.stathis.seriesmania.databinding.FragmentUserProfileBinding
import com.stathis.seriesmania.ui.profile.ProfileActivityViewModel
import com.stathis.seriesmania.ui.profile.navigator.ProfileAction
import com.stathis.seriesmania.ui.results.ResultsActivity
import com.stathis.seriesmania.ui.results.navigator.ResultAction
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserProfileFragment :
    BaseFragment<FragmentUserProfileBinding>(R.layout.fragment_user_profile),
    OtherUserProfileCallback {

    private val viewModel: UserProfileViewModel by viewModels()
    private val activityViewModel: ProfileActivityViewModel by activityViewModels()

    private val adapter = OtherUserProfileAdapter(this)

    override fun init() {
        setScreenTitle("User's Profile")
        binding.adapter = adapter
    }

    override fun startOps() {
        getUserDetails()

        viewModel.userInfo.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> showLoader()
                is Result.Success -> {
                    hideLoader()
                    adapter.submitList(result.data)
                }

                is Result.Failure -> hideLoader()
            }
        }

        viewModel.isFollow.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> showLoader()
                is Result.Success -> {
                    hideLoader()
                    getUserDetails()
                }

                is Result.Failure -> hideLoader()
            }
        }
    }

    override fun stopOps() {}

    override fun onFollowClick(user: User) {
        viewModel.followIconClicked()
    }

    override fun onFollowingUsersClick() {
        activityViewModel.navigateToScreen(ProfileAction.FOLLOWING)
    }

    override fun onFollowersClick() {
        activityViewModel.navigateToScreen(ProfileAction.FOLLOWERS)
    }

    override fun onWatchlistClick() {
        activityViewModel.navigateToScreen(ProfileAction.WATCHLIST)
    }

    override fun onSeriesClick(model: TvSeries) {
        startActivity(Intent(requireContext(), ResultsActivity::class.java).apply {
            putExtra(MODE, ResultAction.DETAILS)
            putExtra(SERIES, model)
        })
    }

    private fun getUserDetails() {
        requireActivity().intent.getParcelable<User>(USER)?.let {
            viewModel.getProfileInfo(it)
        }
    }
}