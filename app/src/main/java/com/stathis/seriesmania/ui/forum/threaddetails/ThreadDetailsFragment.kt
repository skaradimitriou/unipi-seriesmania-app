package com.stathis.seriesmania.ui.forum.threaddetails

import android.content.Intent
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.stathis.core.adapters.forum.ThreadDetailsAdapter
import com.stathis.core.adapters.forum.ThreadsCallback
import com.stathis.core.base.BaseFragment
import com.stathis.core.ext.getParcelable
import com.stathis.core.ext.setScreenTitle
import com.stathis.core.util.MODE
import com.stathis.core.util.THREAD
import com.stathis.core.util.USER
import com.stathis.core.util.decorations.VerticalItemDecoration
import com.stathis.domain.model.forum.ForumThread
import com.stathis.domain.model.profile.User
import com.stathis.seriesmania.R
import com.stathis.seriesmania.databinding.FragmentThreadDetailsBinding
import com.stathis.seriesmania.ui.profile.ProfileActivity
import com.stathis.seriesmania.ui.profile.navigator.ProfileAction
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ThreadDetailsFragment :
    BaseFragment<FragmentThreadDetailsBinding>(R.layout.fragment_thread_details) {

    private val viewModel: ThreadDetailsViewModel by viewModels()

    private val detailsAdapter = ThreadDetailsAdapter(object : ThreadsCallback {
        override fun onAvatarClick(user: User) {
            startActivity(Intent(requireContext(), ProfileActivity::class.java).apply {
                putExtra(MODE, ProfileAction.USER_PROFILE)
                putExtra(USER, user)
            })
        }

        override fun onThreadClick(model: ForumThread) {
            //
        }
    })

    override fun init() {
        setScreenTitle(getString(com.stathis.core.R.string.thread_details_screen_title))

        binding.detailsRecycler.apply {
            adapter = detailsAdapter
            addItemDecoration(VerticalItemDecoration(20))
            itemAnimator = null
        }

        requireActivity().intent.getParcelable<ForumThread>(THREAD)?.let {
            viewModel.getThreadDetails(it)
        }

        binding.threadMessageInput.doAfterTextChanged { input ->
            binding.isEnabled = input?.isNotEmpty()
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getThreadDetails()
        }

        binding.sendBtn.setOnClickListener {
            val reply = binding.threadMessageInput.text.toString()
            binding.threadMessageInput.text.clear()
            viewModel.addReply(reply)
        }
    }

    override fun startOps() {
        viewModel.details.observe(viewLifecycleOwner) { details ->
            binding.swipeRefreshLayout.isRefreshing = false
            detailsAdapter.submitList(details)
        }
    }

    override fun stopOps() {}
}