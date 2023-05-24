package com.stathis.seriesmania.ui.dashboard.discover

import android.content.Intent
import androidx.fragment.app.viewModels
import com.stathis.core.ext.setScreenTitle
import com.stathis.core.util.GENRE
import com.stathis.core.util.RESULT_TYPE
import com.stathis.core.util.decorations.GridItemDecoration
import com.stathis.domain.model.ResultType
import com.stathis.seriesmania.R
import com.stathis.seriesmania.base.BaseFragment
import com.stathis.seriesmania.databinding.FragmentDiscoverBinding
import com.stathis.seriesmania.ui.dashboard.discover.adapter.GenresAdapter
import com.stathis.seriesmania.ui.results.ResultsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiscoverFragment : BaseFragment<FragmentDiscoverBinding>(R.layout.fragment_discover) {

    private val viewModel: DiscoverViewModel by viewModels()

    private val adapter = GenresAdapter { selectedGenre ->
        startActivity(Intent(requireContext(), ResultsActivity::class.java).apply {
            putExtra(GENRE, selectedGenre)
            putExtra(RESULT_TYPE, ResultType.SPECIFIC_GENRE)
        })
    }

    override fun init() {
        setScreenTitle(getString(com.stathis.core.R.string.discover_screen_title))
        binding.adapter = adapter
        binding.discoverRecycler.addItemDecoration(GridItemDecoration(15))

        viewModel.getAvailableGenres()
    }

    override fun startOps() {
        viewModel.genres.observe(viewLifecycleOwner) { genres ->
            adapter.submitList(genres)
        }
    }

    override fun stopOps() {}
}