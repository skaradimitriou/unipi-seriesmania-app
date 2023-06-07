package com.stathis.seriesmania.ui.forum

import android.view.MenuItem
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.stathis.core.base.BaseActivity
import com.stathis.core.ext.getSerializable
import com.stathis.core.util.MODE
import com.stathis.seriesmania.R
import com.stathis.seriesmania.databinding.ActivityForumBinding
import com.stathis.seriesmania.ui.forum.navigator.ForumAction
import com.stathis.seriesmania.ui.forum.navigator.ForumNavigatorImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForumActivity : BaseActivity<ActivityForumBinding>(R.layout.activity_forum) {

    private lateinit var navController: NavController
    private lateinit var navigator: ForumNavigatorImpl

    private val viewModel: ForumViewModel by viewModels()

    override fun init() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navController = findNavController(R.id.nav_host_fragment)
        navigator = ForumNavigatorImpl(this, navController)

        intent.getSerializable<ForumAction>(MODE)?.let {
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