package com.stathis.seriesmania.ui.results

import android.view.MenuItem
import com.stathis.seriesmania.R
import com.stathis.seriesmania.base.BaseActivity
import com.stathis.seriesmania.databinding.ActivityResultsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultsActivity : BaseActivity<ActivityResultsBinding>(R.layout.activity_results) {

    override fun init() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun startOps() {}

    override fun stopOps() {}

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            onBackPressedDispatcher.onBackPressed()
            false
        }
        else -> false
    }
}