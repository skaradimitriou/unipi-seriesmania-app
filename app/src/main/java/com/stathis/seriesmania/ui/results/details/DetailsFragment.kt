package com.stathis.seriesmania.ui.results.details

import androidx.fragment.app.viewModels
import com.stathis.core.ext.getParcelable
import com.stathis.core.ext.setScreenTitle
import com.stathis.core.util.SERIES
import com.stathis.domain.model.TvSeries
import com.stathis.seriesmania.R
import com.stathis.seriesmania.base.BaseFragment
import com.stathis.seriesmania.databinding.FragmentDetailsBinding
import com.stathis.seriesmania.ui.results.details.adapter.DetailsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding>(R.layout.fragment_details) {

    private val viewModel: DetailsViewModel by viewModels()
    private val adapter = DetailsAdapter()

    override fun init() {
        setScreenTitle(getString(com.stathis.core.R.string.details_screen_title))
        binding.adapter = adapter

        requireActivity().intent.getParcelable<TvSeries>(SERIES)?.let {
            viewModel.getCastBySeriesId(it)
        }
    }

    override fun startOps() {
        viewModel.details.observe(viewLifecycleOwner) { detail ->
            adapter.submitList(detail)
        }
    }

    override fun stopOps() {}
}