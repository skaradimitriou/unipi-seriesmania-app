package com.stathis.seriesmania.ui.settings.intro

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.stathis.core.adapters.settings.intro.SettingsIntroAdapter
import com.stathis.core.adapters.settings.intro.SettingsIntroType
import com.stathis.core.base.BaseFragment
import com.stathis.core.ext.setScreenTitle
import com.stathis.core.util.decorations.VerticalItemDecoration
import com.stathis.seriesmania.R
import com.stathis.seriesmania.databinding.FragmentSettingsIntroBinding
import com.stathis.seriesmania.ui.settings.SettingsViewModel
import com.stathis.seriesmania.ui.settings.navigation.SettingsAction
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsIntroFragment :
    BaseFragment<FragmentSettingsIntroBinding>(R.layout.fragment_settings_intro) {

    private val viewModel: SettingsIntroViewModel by viewModels()
    private val activityViewModel: SettingsViewModel by activityViewModels()

    private val settingsIntroAdapter = SettingsIntroAdapter {
        when (it.type) {
            SettingsIntroType.ANALYTICS -> activityViewModel.navigateToScreen(SettingsAction.ANALYTICS)
            SettingsIntroType.FAQ -> activityViewModel.navigateToScreen(SettingsAction.FAQ)
            SettingsIntroType.ABOUT -> activityViewModel.navigateToScreen(SettingsAction.ABOUT)
        }
    }

    override fun init() {
        setScreenTitle(getString(com.stathis.core.R.string.additional_actions_title))
        viewModel.getSettingsItems()

        binding.settingsIntroRecycler.apply {
            addItemDecoration(VerticalItemDecoration(6))
            adapter = settingsIntroAdapter
        }
    }

    override fun startOps() {
        viewModel.options.observe(viewLifecycleOwner) { options ->
            settingsIntroAdapter.submitList(options)
        }
    }

    override fun stopOps() {
        activityViewModel.resetNavigation()
    }
}