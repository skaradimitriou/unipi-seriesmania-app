package com.stathis.seriesmania.ui.profile.results

import android.content.Intent
import androidx.fragment.app.viewModels
import com.stathis.core.adapters.profile.ProfileResultsAdapter
import com.stathis.core.adapters.profile.ProfileResultsCallback
import com.stathis.core.base.BaseFragment
import com.stathis.core.ext.serializable
import com.stathis.core.ext.setScreenTitle
import com.stathis.core.util.MODE
import com.stathis.core.util.SERIES
import com.stathis.core.util.TYPE
import com.stathis.core.util.USER
import com.stathis.core.util.decorations.VerticalItemDecoration
import com.stathis.domain.model.TvSeries
import com.stathis.domain.model.profile.OtherUser
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
        arguments?.serializable<ProfileResultsType>(TYPE)?.let { type ->
            setScreenTitle(type.toScreenTitle(requireContext()))
            viewModel.getResults(type)
        }

        binding.resultsRecycler.apply {
            adapter = profileResultsAdapter
            addItemDecoration(VerticalItemDecoration(20))
            itemAnimator = null
        }
    }

    override fun startOps() {
        viewModel.follows.observe(viewLifecycleOwner) { follows ->
            profileResultsAdapter.submitList(follows)
        }

        viewModel.watchlist.observe(viewLifecycleOwner) { watchlist ->
            profileResultsAdapter.submitList(watchlist)
        }
    }

    override fun stopOps() {}

    override fun onUserClick(user: OtherUser) {
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