package com.stathis.seriesmania.ui.profile.user

import android.content.Intent
import androidx.fragment.app.viewModels
import com.stathis.core.adapters.profile.OtherUserProfileAdapter
import com.stathis.core.adapters.profile.OtherUserProfileCallback
import com.stathis.core.base.BaseFragment
import com.stathis.core.ext.getParcelable
import com.stathis.core.ext.setScreenTitle
import com.stathis.core.util.MODE
import com.stathis.core.util.SERIES
import com.stathis.core.util.USER
import com.stathis.domain.model.TvSeries
import com.stathis.domain.model.profile.OtherUser
import com.stathis.domain.model.profile.User
import com.stathis.seriesmania.R
import com.stathis.seriesmania.databinding.FragmentUserProfileBinding
import com.stathis.seriesmania.ui.results.ResultsActivity
import com.stathis.seriesmania.ui.results.navigator.ResultAction
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class UserProfileFragment :
    BaseFragment<FragmentUserProfileBinding>(R.layout.fragment_user_profile) {

    private val viewModel: UserProfileViewModel by viewModels()

    private val adapter = OtherUserProfileAdapter(object : OtherUserProfileCallback {
        override fun onFollowClick(user: OtherUser) {
            viewModel.followIconClicked()
        }

        override fun onSeriesClick(model: TvSeries) {
            startActivity(Intent(requireContext(), ResultsActivity::class.java).apply {
                putExtra(MODE, ResultAction.DETAILS)
                putExtra(SERIES, model)
            })
        }
    })

    override fun init() {
        setScreenTitle("Προφίλ χρήστη")
        binding.adapter = adapter
    }

    override fun startOps() {
        requireActivity().intent.getParcelable<User>(USER)?.let {
            viewModel.getProfileInfo(it)
        }

        viewModel.userInfo.observe(viewLifecycleOwner) {
            Timber.d("")
            adapter.submitList(it)
        }

        viewModel.isFollow.observe(viewLifecycleOwner) {
            Timber.d("")
        }
    }

    override fun stopOps() {}
}