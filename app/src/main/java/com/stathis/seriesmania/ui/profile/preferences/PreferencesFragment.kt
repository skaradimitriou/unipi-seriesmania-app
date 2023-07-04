package com.stathis.seriesmania.ui.profile.preferences

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.stathis.core.base.BaseFragment
import com.stathis.core.ext.hideLoader
import com.stathis.core.ext.setScreenTitle
import com.stathis.core.ext.showLoader
import com.stathis.core.util.decorations.VerticalItemDecoration
import com.stathis.domain.model.Result
import com.stathis.seriesmania.R
import com.stathis.seriesmania.databinding.FragmentPreferencesBinding
import com.stathis.seriesmania.ui.profile.ProfileActivityViewModel
import com.stathis.seriesmania.ui.profile.navigator.ProfileAction
import com.stathis.seriesmania.ui.profile.preferences.adapter.PreferencesAdapter
import com.stathis.seriesmania.ui.profile.preferences.adapter.PreferencesCallback
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PreferencesFragment :
    BaseFragment<FragmentPreferencesBinding>(R.layout.fragment_preferences), PreferencesCallback {

    private val viewModel: PreferencesViewModel by viewModels()
    private val activityViewModel: ProfileActivityViewModel by activityViewModels()

    private val preferencesAdapter = PreferencesAdapter(this)

    override fun init() {
        setScreenTitle(getString(com.stathis.core.R.string.preferences_title))
        binding.viewModel = viewModel

        viewModel.getPreferences()

        binding.preferencesRecycler.apply {
            addItemDecoration(VerticalItemDecoration(20))
            adapter = preferencesAdapter
            itemAnimator = null
        }

        binding.saveBtn.setOnClickListener {
            viewModel.saveSelection()
        }
    }

    override fun startOps() {
        viewModel.preferences.observe(viewLifecycleOwner) { preferences ->
            preferencesAdapter.submitList(preferences)
        }

        viewModel.preferencesSaved.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> showLoader()
                is Result.Success -> {
                    hideLoader()
                    activityViewModel.navigateToScreen(ProfileAction.PREFERENCES_UPDATED)
                }

                is Result.Failure -> hideLoader()
            }
        }
    }

    override fun stopOps() {}

    override fun onPreferencesClick() {
        viewModel.validateSelection(preferencesAdapter.currentList)
    }
}