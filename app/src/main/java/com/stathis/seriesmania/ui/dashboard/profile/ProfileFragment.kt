package com.stathis.seriesmania.ui.dashboard.profile

import android.content.Intent
import androidx.fragment.app.viewModels
import com.stathis.core.base.BaseFragment
import com.stathis.core.ext.askUserForAction
import com.stathis.core.ext.setScreenTitle
import com.stathis.seriesmania.R
import com.stathis.seriesmania.databinding.FragmentProfileBinding
import com.stathis.seriesmania.ui.onboarding.OnboardingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {

    private val viewModel: ProfileViewModel by viewModels()

    private val adapter = ProfileAdapter {
        askUserForAction(
            title = getString(com.stathis.core.R.string.ask_user_logout),
            btnTitle = getString(com.stathis.core.R.string.logout)
        ) {
            viewModel.logoutUser()
        }
    }

    override fun init() {
        setScreenTitle(getString(com.stathis.core.R.string.profile_title))
//        addAppBarMenu(menuId = R.menu.profile_screen_menu) { selectedAction ->
//            if (selectedAction == R.id.nav_edit_profile) {
//                startActivity(Intent(requireContext(), EditProfileActivity::class.java))
//            }
//        }
        binding.adapter = adapter
    }

    override fun startOps() {
        viewModel.getProfileInfo()

        viewModel.userInfo.observe(viewLifecycleOwner) { uiData ->
            adapter.submitList(uiData)
        }

        viewModel.userLoggedOut.observe(viewLifecycleOwner) { loggedOut ->
            startActivity(Intent(requireContext(), OnboardingActivity::class.java))
            requireActivity().finish()
        }
    }

    override fun stopOps() {}
}