package com.stathis.seriesmania.ui.dashboard.threads

import android.content.Intent
import androidx.fragment.app.viewModels
import com.stathis.core.adapters.forum.ThreadsAdapter
import com.stathis.core.adapters.forum.ThreadsCallback
import com.stathis.core.base.BaseFragment
import com.stathis.core.ext.addAppBarMenu
import com.stathis.core.ext.setScreenTitle
import com.stathis.core.util.MODE
import com.stathis.core.util.THREAD
import com.stathis.core.util.decorations.VerticalItemDecoration
import com.stathis.domain.model.forum.Thread
import com.stathis.domain.model.profile.User
import com.stathis.seriesmania.R
import com.stathis.seriesmania.databinding.FragmentForumBinding
import com.stathis.seriesmania.ui.forum.ForumActivity
import com.stathis.seriesmania.ui.forum.navigator.ForumAction
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ForumFragment : BaseFragment<FragmentForumBinding>(R.layout.fragment_forum) {

    private val viewModel: ForumViewModel by viewModels()

    private val adapter = ThreadsAdapter(object : ThreadsCallback {
        override fun onAvatarClick(user: User) {
            Timber.d("Sel => $user")
        }

        override fun onThreadClick(model: Thread) {
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
        viewModel.threads.observe(viewLifecycleOwner) { data ->
            binding.swipeRefreshLayout.isRefreshing = false
            adapter.submitList(data)
        }
    }

    override fun stopOps() {}
}