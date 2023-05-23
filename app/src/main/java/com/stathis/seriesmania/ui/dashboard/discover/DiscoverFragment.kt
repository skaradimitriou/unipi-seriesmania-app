package com.stathis.seriesmania.ui.dashboard.discover

import androidx.fragment.app.viewModels
import com.stathis.core.ext.setScreenTitle
import com.stathis.core.util.decorations.GridItemDecoration
import com.stathis.seriesmania.R
import com.stathis.seriesmania.base.BaseFragment
import com.stathis.seriesmania.databinding.FragmentDiscoverBinding
import com.stathis.seriesmania.ui.dashboard.discover.adapter.GenresAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class DiscoverFragment : BaseFragment<FragmentDiscoverBinding>(R.layout.fragment_discover) {

    private val viewModel: DiscoverViewModel by viewModels()

    private val adapter = GenresAdapter { selectedGenre ->
        Timber.d("SEL => $selectedGenre")
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