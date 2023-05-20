package com.stathis.seriesmania.ui.results.details

import com.stathis.core.ext.getParcelable
import com.stathis.core.util.SERIES
import com.stathis.domain.model.TvSeries
import com.stathis.seriesmania.R
import com.stathis.seriesmania.base.BaseFragment
import com.stathis.seriesmania.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding>(R.layout.fragment_details) {


    override fun init() {
        requireActivity().intent.getParcelable<TvSeries>(SERIES)?.let {
            binding.model = it
        }
    }

    override fun startOps() {

    }

    override fun stopOps() {

    }
}