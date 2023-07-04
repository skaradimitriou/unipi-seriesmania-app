package com.stathis.seriesmania.ui.settings

import android.view.MenuItem
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.stathis.core.base.BaseActivity
import com.stathis.seriesmania.R
import com.stathis.seriesmania.databinding.ActivitySettingsBinding
import com.stathis.seriesmania.ui.settings.navigation.SettingsNavigatorImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsActivity : BaseActivity<ActivitySettingsBinding>(R.layout.activity_settings) {

    private lateinit var navController: NavController
    private lateinit var navigator: SettingsNavigatorImpl

    private val viewModel: SettingsViewModel by viewModels()

    override fun init() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navController = findNavController(R.id.nav_host_fragment)
        navigator = SettingsNavigatorImpl(this, navController)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            onBackPressedDispatcher.onBackPressed()
            false
        }

        else -> false
    }
}