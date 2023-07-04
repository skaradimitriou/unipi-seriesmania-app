package com.stathis.seriesmania.ui.onboarding.register

import android.content.Intent
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.stathis.core.base.BaseFragment
import com.stathis.core.ext.hideKeyboard
import com.stathis.core.ext.hideLoader
import com.stathis.core.ext.showLoader
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
        binding.viewModel = viewModel

        binding.emailInputField.doAfterTextChanged {
            viewModel.email = it.toString()
            viewModel.validateInput()
        }

        binding.passInputField.doAfterTextChanged {
            viewModel.pass = it.toString()
            viewModel.validateInput()
        }

        binding.confPassInputField.doAfterTextChanged {
            viewModel.confPass = it.toString()
            viewModel.validateInput()
        }

        binding.registerBtn.setOnClickListener {
            viewModel.validateData()
            hideKeyboard()
        }
    }

    override fun startOps() {
        viewModel.registrationResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> showLoader()

                is Result.Success -> {
                    hideLoader()
                    goToAppOnboarding()
                }

                else -> {
                    hideLoader()
                    binding.showSnackbar(result.error.toString())
                }
            }
        }
    }

    override fun stopOps() {}

    private fun goToAppOnboarding() {
        startActivity(Intent(requireContext(), DashboardActivity::class.java))
    }
}