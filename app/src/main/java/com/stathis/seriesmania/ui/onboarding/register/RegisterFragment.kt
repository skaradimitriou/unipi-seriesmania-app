package com.stathis.seriesmania.ui.onboarding.register

import android.content.Intent
import androidx.fragment.app.viewModels
import com.stathis.core.base.BaseFragment
import com.stathis.core.ext.showSnackbar
import com.stathis.domain.model.Result
import com.stathis.seriesmania.R
import com.stathis.seriesmania.databinding.FragmentRegisterBinding
import com.stathis.seriesmania.ui.dashboard.DashboardActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(R.layout.fragment_register) {

    private val viewModel: RegisterViewModel by viewModels()

    override fun init() {
        binding.registerBtn.setOnClickListener {
            val email = binding.emailInputField.text.toString()
            val pass = binding.passInputField.text.toString()
            val confirmPass = binding.passConfInputField.text.toString()
            viewModel.validateData(email, pass, confirmPass)
        }
    }

    override fun startOps() {
        viewModel.registrationResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> binding.registerBtn.isEnabled = false
                is Result.Success -> goToAppOnboarding()
                is Result.Failure -> {
                    binding.showSnackbar(result.error.toString())
                    binding.registerBtn.isEnabled = true
                }
            }
        }
    }

    override fun stopOps() {}

    private fun goToAppOnboarding() {
        startActivity(Intent(requireContext(), DashboardActivity::class.java))
    }
}