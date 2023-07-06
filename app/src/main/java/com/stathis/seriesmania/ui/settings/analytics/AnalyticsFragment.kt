package com.stathis.seriesmania.ui.settings.analytics

import android.content.Intent
import androidx.fragment.app.viewModels
import com.stathis.core.adapters.analytics.AnalyticsAdapter
import com.stathis.core.adapters.analytics.AnalyticsCallback
import com.stathis.core.base.BaseFragment
import com.stathis.core.ext.hideLoader
import com.stathis.core.ext.setScreenTitle
import com.stathis.core.ext.showLoader
import com.stathis.core.util.MODE
import com.stathis.core.util.SERIES
import com.stathis.core.util.THREAD
import com.stathis.core.util.USER
import com.stathis.domain.model.Result
import com.stathis.domain.model.TvSeries
import com.stathis.domain.model.forum.ForumThread
import com.stathis.domain.model.profile.User
import com.stathis.seriesmania.R
import com.stathis.seriesmania.databinding.FragmentAnalyticsBinding
import com.stathis.seriesmania.ui.forum.ForumActivity
import com.stathis.seriesmania.ui.forum.navigator.ForumAction
import com.stathis.seriesmania.ui.profile.ProfileActivity
import com.stathis.seriesmania.ui.profile.navigator.ProfileAction
import com.stathis.seriesmania.ui.results.ResultsActivity
import com.stathis.seriesmania.ui.results.navigator.ResultAction
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AnalyticsFragment : BaseFragment<FragmentAnalyticsBinding>(R.layout.fragment_analytics),
    AnalyticsCallback {

    private val viewModel: AnalyticsViewModel by viewModels()
    private val analyticsAdapter = AnalyticsAdapter(this)

    override fun init() {
        setScreenTitle(getString(com.stathis.core.R.string.analytics_title))

        binding.analyticsRecycler.apply {
            adapter = analyticsAdapter
        }
    }

    override fun startOps() {
        viewModel.fetchAppAnalytics()

        viewModel.analytics.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> showLoader()
                is Result.Success -> {
                    hideLoader()
                    analyticsAdapter.submitList(result.data)
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

    override fun onThreadClick(thread: ForumThread) {
        startActivity(Intent(requireContext(), ForumActivity::class.java).apply {
            putExtra(MODE, ForumAction.THREAD_DETAILS)
            putExtra(THREAD, thread)
        })
    }
}