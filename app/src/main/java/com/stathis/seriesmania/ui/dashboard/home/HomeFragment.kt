package com.stathis.seriesmania.ui.dashboard.home

import android.content.Intent
import androidx.fragment.app.viewModels
import com.stathis.core.base.BaseFragment
import com.stathis.core.ext.setScreenTitle
import com.stathis.core.util.MODE
import com.stathis.core.util.RESULT_TYPE
import com.stathis.core.util.SERIES
import com.stathis.domain.model.ResultType
import com.stathis.domain.model.TvSeries
import com.stathis.seriesmania.R
import com.stathis.seriesmania.databinding.FragmentHomeBinding
import com.stathis.seriesmania.ui.dashboard.home.adapter.DashboardCallback
import com.stathis.seriesmania.ui.dashboard.home.adapter.HomeAdapter
import com.stathis.seriesmania.ui.profile.ProfileActivity
import com.stathis.seriesmania.ui.results.ResultsActivity
import com.stathis.seriesmania.ui.results.navigator.ResultAction
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home), DashboardCallback {

    private val viewModel: HomeViewModel by viewModels()

    private val adapter = HomeAdapter(this)

    override fun init() {
        setScreenTitle(getString(com.stathis.core.R.string.home_title))
        binding.adapter = adapter
    }

    override fun startOps() {
        viewModel.getData()

        viewModel.dashboardData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is com.stathis.domain.model.Result.Loading -> {
                    binding.loading = true
                }
                is com.stathis.domain.model.Result.Success -> {
                    binding.loading = false
                    adapter.submitList(result.data)
                }
                is com.stathis.domain.model.Result.Failure -> {
                    binding.loading = false
                }
            }
        }
    }

    override fun stopOps() {}

    override fun onProfileClick() {
        startActivity(Intent(requireContext(), ProfileActivity::class.java))
    }

    override fun onSeriesClick(model: TvSeries) {
        openResults(landingMode = ResultAction.DETAILS, model = model)
    }

    override fun openAllTopRatedSeries() {
        openResults(landingMode = ResultAction.RESULTS, resultType = ResultType.TOP_RATED)
    }

    override fun openAllTrendingSeries() {
        openResults(landingMode = ResultAction.RESULTS, resultType = ResultType.TRENDING)
    }

    override fun openAllAiringTodaySeries() {
        openResults(landingMode = ResultAction.RESULTS, resultType = ResultType.AIRING_TODAY)
    }

    override fun openMyPreferencesSeries(id: Int) {
        openResults(landingMode = ResultAction.RESULTS, resultType = ResultType.SPECIFIC_GENRE)
    }

    private fun openResults(
        landingMode: ResultAction,
        resultType: ResultType? = null,
        model: TvSeries? = null
    ) {
        startActivity(Intent(requireContext(), ResultsActivity::class.java).apply {
            putExtra(MODE, landingMode)
            putExtra(RESULT_TYPE, resultType)
            putExtra(SERIES, model)
        })
    }
}