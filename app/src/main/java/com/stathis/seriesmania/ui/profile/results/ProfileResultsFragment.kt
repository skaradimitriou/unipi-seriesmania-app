package com.stathis.seriesmania.ui.profile.results

import android.content.Intent
import androidx.fragment.app.viewModels
import com.stathis.core.adapters.profile.ProfileResultsAdapter
import com.stathis.core.adapters.profile.ProfileResultsCallback
import com.stathis.core.base.BaseFragment
import com.stathis.core.ext.getParcelable
import com.stathis.core.ext.hideLoader
import com.stathis.core.ext.serializable
import com.stathis.core.ext.setScreenTitle
import com.stathis.core.ext.showLoader
import com.stathis.core.util.MODE
import com.stathis.core.util.SERIES
import com.stathis.core.util.TYPE
import com.stathis.core.util.USER
import com.stathis.core.util.decorations.VerticalItemDecoration
import com.stathis.domain.model.Result
import com.stathis.domain.model.TvSeries
import com.stathis.domain.model.profile.User
import com.stathis.seriesmania.R
import com.stathis.seriesmania.databinding.FragmentProfileResultsBinding
import com.stathis.seriesmania.ui.profile.ProfileActivity
import com.stathis.seriesmania.ui.profile.navigator.ProfileAction
import com.stathis.seriesmania.ui.profile.results.type.ProfileResultsType
import com.stathis.seriesmania.ui.profile.results.type.toScreenTitle
import com.stathis.seriesmania.ui.results.ResultsActivity
import com.stathis.seriesmania.ui.results.navigator.ResultAction
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileResultsFragment :
    BaseFragment<FragmentProfileResultsBinding>(R.layout.fragment_profile_results),
    ProfileResultsCallback {

    private val viewModel: ProfileResultsViewModel by viewModels()

    private val profileResultsAdapter = ProfileResultsAdapter(this)

    override fun init() {
        binding.resultsRecycler.apply {
            adapter = profileResultsAdapter
            addItemDecoration(VerticalItemDecoration(20))
            itemAnimator = null
        }
    }

    override fun startOps() {
        arguments?.serializable<ProfileResultsType>(TYPE)?.let { type ->
            setScreenTitle(type.toScreenTitle(requireContext()))
            val userId = requireActivity().intent.getParcelable<User>(USER)
            viewModel.getResults(type, userId?.id)
        }

        viewModel.follows.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> showLoader()
                is Result.Success -> {
                    hideLoader()
                    profileResultsAdapter.submitList(result.data)
                }

                is Result.Failure -> hideLoader()
            }
        }

        viewModel.watchlist.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> showLoader()
                is Result.Success -> {
                    hideLoader()
                    profileResultsAdapter.submitList(result.data)
                }

                is Result.Failure -> hideLoader()
            }
        }
    }

    override fun stopOps() {}

    override fun onUserClick(user: User) {
        startActivity(Intent(requireContext(), ProfileActivity::class.java).apply {
            putExtra(MODE, ProfileAction.USER_PROFILE)
            putExtra(USER, user)
        })
    }

    override fun onSeriesClick(series: TvSeries) {
        startActivity(Intent(requireContext(), ResultsActivity::class.java).apply {
            putExtra(MODE, ResultAction.DETAILS)
            putExtra(SERIES, series)
        })
    }
}