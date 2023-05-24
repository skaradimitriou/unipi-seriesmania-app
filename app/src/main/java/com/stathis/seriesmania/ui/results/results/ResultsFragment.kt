package com.stathis.seriesmania.ui.results.results

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.stathis.core.ext.getParcelable
import com.stathis.core.ext.getSerializable
import com.stathis.core.ext.setScreenTitle
import com.stathis.core.ext.toDisplayText
import com.stathis.core.util.GENRE
import com.stathis.core.util.MODE
import com.stathis.core.util.RESULT_TYPE
import com.stathis.core.util.SERIES
import com.stathis.core.util.decorations.VerticalItemDecoration
import com.stathis.domain.model.ResultType
import com.stathis.domain.model.genres.Genre
import com.stathis.seriesmania.R
import com.stathis.seriesmania.base.BaseFragment
import com.stathis.seriesmania.databinding.FragmentResultsBinding
import com.stathis.seriesmania.ui.results.ResultsActivity
import com.stathis.seriesmania.ui.results.navigator.ResultAction
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ResultsFragment : BaseFragment<FragmentResultsBinding>(R.layout.fragment_results) {

    private val viewModel: ResultsViewModel by viewModels()

    private val adapter = ResultsPagingAdapter {
        startActivity(Intent(requireContext(), ResultsActivity::class.java).apply {
            putExtra(MODE, ResultAction.DETAILS)
            putExtra(SERIES, it)
        })
    }

    override fun init() {
        binding.adapter = adapter
        binding.detailsRecycler.addItemDecoration(VerticalItemDecoration(20))

        requireActivity().intent.getSerializable<ResultType>(RESULT_TYPE)?.let { type ->
            val genre = requireActivity().intent.getParcelable<Genre>(GENRE)

            genre?.let {
                setScreenTitle(getString(com.stathis.core.R.string.all_series_genre_placeholder, genre.name))
            } ?: kotlin.run {
                setScreenTitle(type.toDisplayText(requireContext()))
            }

            viewModel.getResultsForGenre(genre = genre, resultType = type)
        }
    }

    override fun startOps() {
        viewModel.data.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                adapter.submitData(it)
            }
        }
    }

    override fun stopOps() {}
}
