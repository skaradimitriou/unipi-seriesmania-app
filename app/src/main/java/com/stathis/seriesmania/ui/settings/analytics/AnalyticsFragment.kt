package com.stathis.seriesmania.ui.settings.analytics

import androidx.fragment.app.viewModels
import com.stathis.core.adapters.analytics.AnalyticsAdapter
import com.stathis.core.adapters.analytics.AnalyticsCallback
import com.stathis.core.base.BaseFragment
import com.stathis.core.ext.setScreenTitle
import com.stathis.core.util.decorations.VerticalItemDecoration
import com.stathis.seriesmania.R
import com.stathis.seriesmania.databinding.FragmentAnalyticsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AnalyticsFragment : BaseFragment<FragmentAnalyticsBinding>(R.layout.fragment_analytics),
    AnalyticsCallback {

    private val viewModel: AnalyticsViewModel by viewModels()
    private val analyticsAdapter = AnalyticsAdapter(this)

    override fun init() {
        setScreenTitle(getString(com.stathis.core.R.string.analytics_title))

        binding.analyticsRecycler.apply {
            addItemDecoration(VerticalItemDecoration(10))
            adapter = analyticsAdapter
        }
    }

    override fun startOps() {
        viewModel.fetchAppAnalytics()

        viewModel.analytics.observe(viewLifecycleOwner) { uiList ->
            analyticsAdapter.submitList(uiList)
        }
    }

    override fun stopOps() {}

    override fun onUserClick() {
        //
    }

    override fun onSeriesClick() {
        //
    }
}