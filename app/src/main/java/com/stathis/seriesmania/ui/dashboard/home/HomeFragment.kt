package com.stathis.seriesmania.ui.dashboard.home

import androidx.fragment.app.viewModels
import com.stathis.seriesmania.R
import com.stathis.seriesmania.base.BaseFragment
import com.stathis.seriesmania.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()

    override fun init() {
        viewModel.getData()
        Timber.d("DATA")
    }

    override fun startOps() {

    }

    override fun stopOps() {

    }
}