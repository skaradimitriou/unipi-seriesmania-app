package com.stathis.seriesmania.ui.profile.update

import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.stathis.core.base.BaseFragment
import com.stathis.core.ext.setScreenTitle
import com.stathis.seriesmania.R
import com.stathis.seriesmania.databinding.FragmentUpdateInfoBinding
import com.stathis.seriesmania.ui.profile.ProfileActivityViewModel
import com.stathis.seriesmania.ui.profile.navigator.ProfileAction
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateInfoFragment : BaseFragment<FragmentUpdateInfoBinding>(R.layout.fragment_update_info) {

    private val viewModel: UpdateInfoViewModel by viewModels()
    private val activityViewModel: ProfileActivityViewModel by activityViewModels()

    override fun init() {
        setScreenTitle(getString(com.stathis.core.R.string.update_info_title))
        binding.viewModel = viewModel

        binding.usernameInputTxt.doAfterTextChanged {
            viewModel.validateInput(username = it.toString())
        }

        binding.bioInputTxt.doAfterTextChanged {
            viewModel.validateInput(bio = it.toString())
        }

        binding.saveBtn.setOnClickListener {
            viewModel.saveUserData()
        }
    }

    override fun startOps() {
        viewModel.userInfo.observe(viewLifecycleOwner) { model ->
            binding.usernameInputTxt.setText(model.username)
            binding.bioInputTxt.setText(model.bio)
        }

        viewModel.profileUpdated.observe(viewLifecycleOwner) {
            activityViewModel.navigateToScreen(ProfileAction.PROFILE_INFO_UPDATED)
        }
    }

    override fun stopOps() {}
}