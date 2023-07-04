package com.stathis.seriesmania.ui.settings.about

import com.stathis.core.base.BaseFragment
import com.stathis.core.ext.setScreenTitle
import com.stathis.seriesmania.R
import com.stathis.seriesmania.databinding.FragmentAboutBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AboutFragment : BaseFragment<FragmentAboutBinding>(R.layout.fragment_about) {

    override fun init() {
        setScreenTitle(getString(com.stathis.core.R.string.about_app_title))
    }

    override fun startOps() {}
    override fun stopOps() {}
}