package com.stathis.seriesmania.ui.results.details

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.stathis.core.ext.getParcelable
import com.stathis.core.ext.setScreenTitle
import com.stathis.core.util.SERIES
import com.stathis.domain.model.TvSeries
import com.stathis.domain.model.cast.Cast
import com.stathis.seriesmania.R
import com.stathis.seriesmania.base.BaseFragment
import com.stathis.seriesmania.databinding.FragmentDetailsBinding
import com.stathis.seriesmania.ui.results.ResultsActivityViewModel
import com.stathis.seriesmania.ui.results.ResultsSharedViewModel
import com.stathis.seriesmania.ui.results.details.adapter.DetailsAdapter
import com.stathis.seriesmania.ui.results.details.adapter.DetailsCallback
import com.stathis.seriesmania.ui.results.navigator.ResultAction
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding>(R.layout.fragment_details) {

    private val viewModel: DetailsViewModel by viewModels()
    private val sharedViewModel: ResultsSharedViewModel by activityViewModels()
    private val activityViewModel: ResultsActivityViewModel by activityViewModels()

    private val adapter = DetailsAdapter(object : DetailsCallback {
        override fun onSeriesClick(series: TvSeries) {
            viewModel.getSeriesInfo(series)
        }

        override fun onActorClick(actor: Cast) {
            sharedViewModel.setPersonId(actor)
            activityViewModel.navigateToScreen(ResultAction.CAST_DETAILS)
        }
    })

    override fun init() {
        setScreenTitle(getString(com.stathis.core.R.string.details_screen_title))
        binding.adapter = adapter

        requireActivity().intent.getParcelable<TvSeries>(SERIES)?.let {
            viewModel.getSeriesInfo(it)
        }
    }

    override fun startOps() {
        viewModel.details.observe(viewLifecycleOwner) { detail ->
            adapter.submitList(detail)
        }
    }

    override fun stopOps() {}
}