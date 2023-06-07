package com.stathis.core.adapters.forum

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.stathis.core.base.BaseDiffUtil
import com.stathis.core.base.BaseViewHolder
import com.stathis.core.databinding.HolderThreadItemBinding
import com.stathis.domain.model.UiModel
import com.stathis.domain.model.forum.Thread
import com.stathis.domain.model.profile.User

class ThreadsAdapter(
    private val callback: ThreadsCallback
) : ListAdapter<UiModel, ThreadsViewHolder>(BaseDiffUtil<UiModel>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThreadsViewHolder {
        val view = HolderThreadItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ThreadsViewHolder(view, callback)
    }

    override fun onBindViewHolder(holder: ThreadsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ThreadsViewHolder(
    private val binding: HolderThreadItemBinding, private val callback: ThreadsCallback
) : BaseViewHolder(binding) {

    override fun present(data: UiModel) {
        when (data) {
            is Thread -> {
                binding.model = data
                binding.callback = callback
            }
        }
    }
}

interface ThreadsCallback {
    fun onAvatarClick(user: User)
    fun onThreadClick(model: Thread)
}