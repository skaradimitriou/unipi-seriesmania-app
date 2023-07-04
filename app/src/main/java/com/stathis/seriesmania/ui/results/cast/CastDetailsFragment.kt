package com.stathis.seriesmania.ui.results.cast

import android.content.Intent
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.stathis.core.base.BaseFragment
import com.stathis.core.ext.hideLoader
import com.stathis.core.ext.setScreenTitle
import com.stathis.core.ext.showLoader
import com.stathis.core.util.MODE
import com.stathis.core.util.SERIES
import com.stathis.domain.model.Result
import com.stathis.seriesmania.R
import com.stathis.seriesmania.databinding.FragmentCastDetailsBinding
import com.stathis.seriesmania.ui.results.ResultsActivity
import com.stathis.seriesmania.ui.results.ResultsSharedViewModel
import com.stathis.seriesmania.ui.results.cast.adapter.CastDetailsAdapter
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
        viewModel.details.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> showLoader()
                is Result.Success -> {
                    hideLoader()
                    detailsAdapter.submitList(result.data)
                }

                is Result.Failure -> hideLoader()
            }
        }
    }

    override fun stopOps() {}
}