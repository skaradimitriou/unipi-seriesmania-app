package com.stathis.seriesmania.ui.onboarding

import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.stathis.core.base.BaseActivity
import com.stathis.core.ext.hideActionBar
import com.stathis.seriesmania.R
import com.stathis.seriesmania.databinding.ActivityOnboardingBinding
import com.stathis.seriesmania.ui.onboarding.navigator.OnboardingNavigatorImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingActivity : BaseActivity<ActivityOnboardingBinding>(R.layout.activity_onboarding) {

    private val viewModel: OnboardingSharedViewModel by viewModels()

    private lateinit var navController: NavController
    private lateinit var navigator: OnboardingNavigatorImpl

    override fun init() {
        hideActionBar()
        navController = findNavController(R.id.nav_host_fragment)
        navigator = OnboardingNavigatorImpl(this, navController)

        navController.addOnDestinationChangedListener { _, _, _ ->
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
    }

    override fun startOps() {
        viewModel.navigatorState.observe(this) { action ->
            action?.let { navigator.navigateTo(it) }
        }
    }

    override fun stopOps() {
        viewModel.navigatorState.removeObservers(this)
    }

    override fun showLoader() {
        binding.loading = true
    }

    override fun hideLoader() {
        binding.loading = false
    }

    override fun onBackPressed() {
        navigator.goBack()
        viewModel.resetNavigation()
    }
}