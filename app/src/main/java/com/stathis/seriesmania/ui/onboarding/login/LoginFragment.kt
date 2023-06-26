package com.stathis.seriesmania.ui.onboarding.login

import android.content.Intent
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.stathis.core.base.BaseFragment
import com.stathis.core.ext.showSnackbar
import com.stathis.domain.model.Result
import com.stathis.seriesmania.R
import com.stathis.seriesmania.databinding.FragmentLoginBinding
import com.stathis.seriesmania.ui.dashboard.DashboardActivity
import com.stathis.seriesmania.ui.onboarding.OnboardingSharedViewModel
import com.stathis.seriesmania.ui.onboarding.navigator.OnboardingAction
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    private val viewModel: LoginViewModel by viewModels()

    private val sharedViewModel: OnboardingSharedViewModel by activityViewModels()

    override fun init() {
        binding.loginBtn.setOnClickListener {
            val email = binding.emailField.text.toString()
            val pass = binding.passField.text.toString()

            viewModel.login(email, pass)
        }

        binding.registerBtn.setOnClickListener {
            sharedViewModel.navigateToScreen(OnboardingAction.REGISTER)
        }
    }

    override fun startOps() {
        viewModel.loginResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> binding.loginBtn.isEnabled = false
                is Result.Success -> goToDashboard()
                is Result.Failure -> {
                    binding.loginBtn.isEnabled = true
                    binding.showSnackbar(result.error.toString())
                }
            }
        }
    }

    override fun stopOps() {}

    private fun goToDashboard() {
        startActivity(Intent(requireContext(), DashboardActivity::class.java))
        requireActivity().finish()
    }
}