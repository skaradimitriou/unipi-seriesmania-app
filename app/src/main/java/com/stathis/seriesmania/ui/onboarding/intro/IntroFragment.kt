package com.stathis.seriesmania.ui.onboarding.intro

import android.content.Intent
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.stathis.seriesmania.R
import com.stathis.seriesmania.base.BaseFragment
import com.stathis.seriesmania.databinding.FragmentIntroBinding
import com.stathis.seriesmania.ui.dashboard.DashboardActivity
import com.stathis.seriesmania.ui.onboarding.OnboardingSharedViewModel
import com.stathis.seriesmania.ui.onboarding.navigator.OnboardingAction
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroFragment : BaseFragment<FragmentIntroBinding>(R.layout.fragment_intro) {

    private val viewModel: IntroViewModel by viewModels()
    private val sharedViewModel: OnboardingSharedViewModel by activityViewModels()

    override fun init() {}

    override fun startOps() {
        viewModel.getCurrentUser()
        viewModel.userIsLoggedIn.observe(this) { isLoggedIn ->
            if (isLoggedIn) {
                startActivity(Intent(requireContext(), DashboardActivity::class.java))
                requireActivity().finish()
            } else {
                sharedViewModel.navigateToScreen(OnboardingAction.LOGIN)
            }
        }
    }

    override fun stopOps() {}
}