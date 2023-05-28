package com.stathis.seriesmania.ui.results.cast

import android.content.Intent
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.stathis.core.ext.setScreenTitle
import com.stathis.core.util.MODE
import com.stathis.core.util.SERIES
import com.stathis.seriesmania.R
import com.stathis.seriesmania.base.BaseFragment
import com.stathis.seriesmania.databinding.FragmentCastDetailsBinding
import com.stathis.seriesmania.ui.results.ResultsActivity
import com.stathis.seriesmania.ui.results.ResultsSharedViewModel
import com.stathis.seriesmania.ui.results.navigator.ResultAction
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CastDetailsFragment :
    BaseFragment<FragmentCastDetailsBinding>(R.layout.fragment_cast_details) {

    private val viewModel: CastDetailsViewModel by viewModels()
    private val sharedVM: ResultsSharedViewModel by activityViewModels()

    private val detailsAdapter = CastDetailsAdapter {
        startActivity(Intent(requireContext(), ResultsActivity::class.java).apply {
            putExtra(MODE, ResultAction.DETAILS)
            putExtra(SERIES, it)
        })
    }

    override fun init() {
        setScreenTitle(getString(com.stathis.core.R.string.actor_details_title))
        binding.adapter = detailsAdapter

        sharedVM.getPerson()?.let {
            viewModel.getData(it.id)
        }
    }

    override fun startOps() {
        viewModel.details.observe(viewLifecycleOwner) { data ->
            detailsAdapter.submitList(data)
        }
    }

    override fun stopOps() {}
}