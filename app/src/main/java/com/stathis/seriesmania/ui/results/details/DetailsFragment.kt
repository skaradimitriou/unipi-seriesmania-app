package com.stathis.seriesmania.ui.results.details

import android.view.Menu
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.stathis.core.adapters.details.DetailsAdapter
import com.stathis.core.adapters.details.DetailsCallback
import com.stathis.core.base.BaseFragment
import com.stathis.core.components.GenericRatingBottomSheet
import com.stathis.core.ext.getAppropriateIcon
import com.stathis.core.ext.getDrawable
import com.stathis.core.ext.getItemOrNull
import com.stathis.core.ext.getParcelable
import com.stathis.core.ext.hideLoader
import com.stathis.core.ext.setMenuProvider
import com.stathis.core.ext.setScreenTitle
import com.stathis.core.ext.showLoader
import com.stathis.core.ext.toNotNull
import com.stathis.core.util.SERIES
import com.stathis.domain.model.Result
import com.stathis.domain.model.TvSeries
import com.stathis.domain.model.cast.Cast
import com.stathis.seriesmania.R
import com.stathis.seriesmania.databinding.FragmentDetailsBinding
import com.stathis.seriesmania.ui.results.ResultsActivityViewModel
import com.stathis.seriesmania.ui.results.ResultsSharedViewModel
import com.stathis.seriesmania.ui.results.navigator.ResultAction
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding>(R.layout.fragment_details) {

    private val viewModel: DetailsViewModel by viewModels()
    private val sharedViewModel: ResultsSharedViewModel by activityViewModels()
    private val activityViewModel: ResultsActivityViewModel by activityViewModels()

    private var menu: Menu? = null

    private val adapter = DetailsAdapter(object : DetailsCallback {
        override fun onRateClick() = openRatingBottomSheet()

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
        setMenuProvider(
            menuId = R.menu.series_details_menu,
            onMenuCreated = { menu = it },
            onItemSelected = {
                if (it.itemId == R.id.favoriteIcon) {
                    viewModel.favoriteIconClicked()
                }
            }
        )

        binding.adapter = adapter

        requireActivity().intent.getParcelable<TvSeries>(SERIES)?.let {
            viewModel.getSeriesInfo(it)
        }
    }

    override fun startOps() {
        viewModel.details.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> showLoader()

                is Result.Success -> {
                    hideLoader()
                    adapter.submitList(result.data)
                }

                is Result.Failure -> hideLoader()
            }
        }

        viewModel.ratingInProgress.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> showLoader()
                else -> hideLoader()
            }
        }

        viewModel.isFavorite.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> showLoader()

                is Result.Success -> {
                    hideLoader()
                    val drawable = getAppropriateIcon(result.data.toNotNull())
                    menu?.getItemOrNull(0)?.apply {
                        icon = getDrawable(drawable)
                    }
                }

                is Result.Failure -> hideLoader()
            }
        }
    }

    override fun stopOps() {
        activityViewModel.resetNavigation()
    }

    private fun openRatingBottomSheet() {
        GenericRatingBottomSheet.Builder()
            .setTitle(getString(com.stathis.core.R.string.rating_bs_title))
            .setDescription(getString(com.stathis.core.R.string.rating_bs_desc))
            .setBtnText(getString(com.stathis.core.R.string.rating_bs_btn))
            .setListener { rating, reviewBody ->
                viewModel.rateSeries(rating, reviewBody)
            }
            .build()
            .show(requireActivity().supportFragmentManager, GenericRatingBottomSheet.TAG)
    }
}