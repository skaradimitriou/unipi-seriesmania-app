package com.stathis.seriesmania.ui.dashboard.home

import android.content.Intent
import androidx.fragment.app.viewModels
import com.stathis.core.ext.setScreenTitle
import com.stathis.core.util.SERIES
import com.stathis.seriesmania.R
import com.stathis.seriesmania.base.BaseFragment
import com.stathis.seriesmania.databinding.FragmentHomeBinding
import com.stathis.seriesmania.ui.dashboard.home.adapter.HomeAdapter
import com.stathis.seriesmania.ui.results.ResultsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()

    private val adapter = HomeAdapter { selectedSeries ->
        startActivity(Intent(requireContext(), ResultsActivity::class.java).apply {
            putExtra(SERIES, selectedSeries)
        })
    }

    override fun init() {
        viewModel.getData()
        setScreenTitle(getString(com.stathis.core.R.string.home_title))
        binding.adapter = adapter
    }

    override fun startOps() {
        viewModel.dashboardData.observe(viewLifecycleOwner) { data ->
            adapter.submitList(data)
        }
    }

    override fun stopOps() {}
}