package com.stathis.seriesmania.ui.profile

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.stathis.core.base.BaseActivity
import com.stathis.core.ext.getParcelable
import com.stathis.core.ext.getSerializable
import com.stathis.core.util.MODE
import com.stathis.core.util.USER
import com.stathis.domain.model.profile.User
import com.stathis.seriesmania.R
import com.stathis.seriesmania.databinding.ActivityProfileBinding
import com.stathis.seriesmania.ui.profile.navigator.ProfileAction
import com.stathis.seriesmania.ui.profile.navigator.ProfileNavigatorImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : BaseActivity<ActivityProfileBinding>(R.layout.activity_profile) {

    private lateinit var navController: NavController
    private lateinit var navigator: ProfileNavigatorImpl

    private val viewModel: ProfileActivityViewModel by viewModels()

    override fun init() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navController = findNavController(R.id.nav_host_fragment)
        navigator = ProfileNavigatorImpl(this, navController)

        intent.getSerializable<ProfileAction>(MODE)?.let { action ->
            viewModel.resetNavigation()
            intent.getParcelable<User>(USER)?.let { user ->
                if (viewModel.isItMe(user.id)) {
                    navigator.init(ProfileAction.MY_PROFILE)
                } else {
                    navigator.init(action)
                }
            }
        }
    }

    override fun startOps() {
        viewModel.navigatorState.observe(this) { action ->
            action?.let {
                val bundle = Bundle().apply {
                    putParcelable(USER, intent.getParcelable<User>(USER))
                }
                navigator.navigateTo(it, bundle)
            }
        }
    }

    override fun stopOps() {
        viewModel.resetNavigation()
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