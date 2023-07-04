package com.stathis.seriesmania.ui.results

import android.view.MenuItem
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.stathis.core.base.BaseActivity
import com.stathis.core.ext.getSerializable
import com.stathis.core.util.MODE
import com.stathis.seriesmania.R
import com.stathis.seriesmania.databinding.ActivityResultsBinding
import com.stathis.seriesmania.ui.results.navigator.ResultAction
import com.stathis.seriesmania.ui.results.navigator.ResultNavigatorImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultsActivity : BaseActivity<ActivityResultsBinding>(R.layout.activity_results) {

    private lateinit var navController: NavController
    private lateinit var navigator: ResultNavigatorImpl

    private val viewModel: ResultsActivityViewModel by viewModels()
    private val sharedViewModel: ResultsSharedViewModel by viewModels()

    override fun init() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navController = findNavController(R.id.nav_host_fragment)
        navigator = ResultNavigatorImpl(this, navController)

        intent.getSerializable<ResultAction>(MODE)?.let {
            navigator.init(it)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            onBackPressedDispatcher.onBackPressed()
            false
        }

        else -> false
    }
}