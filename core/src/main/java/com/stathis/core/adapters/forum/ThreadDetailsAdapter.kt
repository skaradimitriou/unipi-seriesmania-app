package com.stathis.core.adapters.forum

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.stathis.core.BR
import com.stathis.core.R
import com.stathis.core.base.BaseDiffUtil
import com.stathis.core.base.BaseViewHolder
import com.stathis.core.databinding.HolderEmptyViewBinding
import com.stathis.core.databinding.HolderThreadItemBinding
import com.stathis.core.databinding.HolderThreadReplyBinding
import com.stathis.domain.model.UiModel
import com.stathis.domain.model.forum.ThreadReply

class ThreadDetailsAdapter(
    private val callback: ThreadsCallback
) : ListAdapter<UiModel, ThreadDetailsViewHolder>(BaseDiffUtil<UiModel>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThreadDetailsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = when (viewType) {
            R.layout.holder_thread_item -> HolderThreadItemBinding.inflate(inflater, parent, false)
            R.layout.holder_thread_reply -> HolderThreadReplyBinding.inflate(
                inflater,
                parent,
                false
            )
            else -> HolderEmptyViewBinding.inflate(inflater, parent, false)
        }
        return ThreadDetailsViewHolder(view, callback)
    }

    override fun onBindViewHolder(holder: ThreadDetailsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is com.stathis.domain.model.forum.Thread -> R.layout.holder_thread_item
        is ThreadReply -> R.layout.holder_thread_reply
        else -> R.layout.holder_empty_view
    }
}

class ThreadDetailsViewHolder(
    private val binding: ViewDataBinding,
    private val callback: ThreadsCallback
) : BaseViewHolder(binding) {

    override fun present(data: UiModel) {
        when (data) {
            is com.stathis.domain.model.forum.Thread -> {
                binding.setVariable(BR.model, data)
                binding.setVariable(BR.callback, callback)
            }
            is ThreadReply -> {
                binding.setVariable(BR.model, data)
                binding.setVariable(BR.callback, callback)
            }
        }
    }
}