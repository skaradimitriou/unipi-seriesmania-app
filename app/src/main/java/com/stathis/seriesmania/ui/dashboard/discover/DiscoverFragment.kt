package com.stathis.seriesmania.ui.dashboard.discover

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.stathis.core.base.BaseFragment
import com.stathis.core.ext.addSearchBarMenu
import com.stathis.core.ext.removeItemDecorations
import com.stathis.core.ext.setScreenTitle
import com.stathis.core.util.GENRE
import com.stathis.core.util.MODE
import com.stathis.core.util.RESULT_TYPE
import com.stathis.core.util.SERIES
import com.stathis.core.util.decorations.GridItemDecoration
import com.stathis.core.util.decorations.VerticalItemDecoration
import com.stathis.domain.model.ResultType
import com.stathis.seriesmania.R
import com.stathis.seriesmania.databinding.FragmentDiscoverBinding
import com.stathis.seriesmania.ui.dashboard.discover.adapter.GenresAdapter
import com.stathis.seriesmania.ui.dashboard.discover.adapter.SearchAdapter
import com.stathis.seriesmania.ui.results.ResultsActivity
import com.stathis.seriesmania.ui.results.navigator.ResultAction
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiscoverFragment : BaseFragment<FragmentDiscoverBinding>(R.layout.fragment_discover) {

    private val viewModel: DiscoverViewModel by viewModels()

    private val genresAdapter = GenresAdapter { selectedGenre ->
        startActivity(Intent(requireContext(), ResultsActivity::class.java).apply {
            putExtra(GENRE, selectedGenre)
            putExtra(RESULT_TYPE, ResultType.SPECIFIC_GENRE)
        })
    }

    private val searchAdapter = SearchAdapter { item ->
        startActivity(Intent(requireContext(), ResultsActivity::class.java).apply {
            putExtra(MODE, ResultAction.DETAILS)
            putExtra(SERIES, item)
        })
    }

    override fun init() {
        setScreenTitle(getString(com.stathis.core.R.string.discover_screen_title))
        addSearchBarMenu(menuId = com.stathis.core.R.menu.search_menu) { query ->
            if (query.isNotEmpty()) {
                viewModel.searchForQuery(query)
            } else {
                viewModel.getAvailableGenres()
            }
        }
    }

    override fun startOps() {
        viewModel.getAvailableGenres()

        viewModel.genres.observe(viewLifecycleOwner) { genres ->
            setupGridList()
            genresAdapter.submitList(genres)
        }

        viewModel.data.observe(viewLifecycleOwner) { data ->
            setUpVerticalList()
            searchAdapter.submitList(data)
        }
    }

    override fun stopOps() {}

    private fun setUpVerticalList() {
        binding.discoverRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            removeItemDecorations()
            addItemDecoration(VerticalItemDecoration(15))
            adapter = searchAdapter
        }
    }

    private fun setupGridList() {
        binding.discoverRecycler.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            removeItemDecorations()
            addItemDecoration(GridItemDecoration(15))
            adapter = genresAdapter
        }
    }
}