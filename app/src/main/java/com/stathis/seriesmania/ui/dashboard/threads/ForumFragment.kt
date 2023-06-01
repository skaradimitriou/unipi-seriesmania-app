package com.stathis.seriesmania.ui.dashboard.threads

import androidx.fragment.app.viewModels
import com.stathis.core.adapters.forum.ThreadsAdapter
import com.stathis.core.adapters.forum.ThreadsCallback
import com.stathis.core.base.BaseFragment
import com.stathis.core.ext.setScreenTitle
import com.stathis.core.util.decorations.VerticalItemDecoration
import com.stathis.domain.model.forum.ForumThread
import com.stathis.domain.model.profile.User
import com.stathis.seriesmania.R
import com.stathis.seriesmania.databinding.FragmentForumBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ForumFragment : BaseFragment<FragmentForumBinding>(R.layout.fragment_forum) {

    private val viewModel: ForumViewModel by viewModels()

    private val adapter = ThreadsAdapter(object : ThreadsCallback {
        override fun onAvatarClick(user: User) {
            Timber.d("Sel => $user")
        }

        override fun onThreadClick(model: ForumThread) {
            Timber.d("Sel => $model")
        }
    })

    override fun init() {
        setScreenTitle(getString(com.stathis.core.R.string.forum_screen_title))
        binding.adapter = adapter
        binding.threadsRecycler.addItemDecoration(VerticalItemDecoration(20))
        viewModel.fetchThreads()
    }

    override fun startOps() {
        viewModel.threads.observe(viewLifecycleOwner) { data ->
            adapter.submitList(data)
        }
    }

    override fun stopOps() {}
}