package com.stathis.seriesmania.ui.dashboard.threads

import android.content.Intent
import androidx.fragment.app.viewModels
import com.stathis.core.adapters.forum.ThreadsAdapter
import com.stathis.core.adapters.forum.ThreadsCallback
import com.stathis.core.base.BaseFragment
import com.stathis.core.ext.addAppBarMenu
import com.stathis.core.ext.hideLoader
import com.stathis.core.ext.setScreenTitle
import com.stathis.core.ext.showLoader
import com.stathis.core.util.MODE
import com.stathis.core.util.THREAD
import com.stathis.core.util.USER
import com.stathis.core.util.decorations.VerticalItemDecoration
import com.stathis.domain.model.Result
import com.stathis.domain.model.forum.ForumThread
import com.stathis.domain.model.profile.User
import com.stathis.seriesmania.R
import com.stathis.seriesmania.databinding.FragmentForumBinding
import com.stathis.seriesmania.ui.forum.ForumActivity
import com.stathis.seriesmania.ui.forum.navigator.ForumAction
import com.stathis.seriesmania.ui.profile.ProfileActivity
import com.stathis.seriesmania.ui.profile.navigator.ProfileAction
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForumFragment : BaseFragment<FragmentForumBinding>(R.layout.fragment_forum) {

    private val viewModel: ForumViewModel by viewModels()

    private val adapter = ThreadsAdapter(object : ThreadsCallback {
        override fun onAvatarClick(user: User) {
            startActivity(Intent(requireContext(), ProfileActivity::class.java).apply {
                putExtra(MODE, ProfileAction.USER_PROFILE)
                putExtra(USER, user)
            })
        }

        override fun onThreadClick(model: ForumThread) {
            startActivity(Intent(requireContext(), ForumActivity::class.java).apply {
                putExtra(MODE, ForumAction.THREAD_DETAILS)
                putExtra(THREAD, model)
            })
        }
    })

    override fun init() {
        setScreenTitle(getString(com.stathis.core.R.string.forum_screen_title))
        binding.adapter = adapter

        binding.threadsRecycler.apply {
            itemAnimator = null
            addItemDecoration(VerticalItemDecoration(20))
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.fetchThreads()
        }

        addAppBarMenu(menuId = R.menu.forum_landing_menu) {
            startActivity(Intent(requireContext(), ForumActivity::class.java).apply {
                putExtra(MODE, ForumAction.ADD_NEW)
            })
        }
    }

    override fun startOps() {
        viewModel.fetchThreads()
        viewModel.threads.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> showLoader()
                is Result.Success -> {
                    hideLoader()
                    binding.swipeRefreshLayout.isRefreshing = false
                    adapter.submitList(result.data)
                }

                is Result.Failure -> hideLoader()
            }
        }
    }

    override fun stopOps() {}
}