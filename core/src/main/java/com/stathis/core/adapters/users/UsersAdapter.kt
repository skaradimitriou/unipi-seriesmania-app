package com.stathis.core.adapters.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.stathis.core.adapters.analytics.uimodel.AnalyticsUsersChildItem
import com.stathis.core.base.BaseDiffUtil
import com.stathis.core.base.BaseViewHolder
import com.stathis.core.databinding.HolderAnalyticsSmallUserBinding
import com.stathis.domain.model.UiModel
import com.stathis.domain.model.profile.User

class UsersAdapter(
    private val callback: UsersCalback
) : ListAdapter<UiModel, UsersViewHolder>(BaseDiffUtil<UiModel>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val view = HolderAnalyticsSmallUserBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return UsersViewHolder(view, callback)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}


class UsersViewHolder(
    private val binding: HolderAnalyticsSmallUserBinding,
    private val callback: UsersCalback
) : BaseViewHolder(binding) {

    override fun present(data: UiModel) = when (data) {
        is AnalyticsUsersChildItem -> {
            binding.model = data
            binding.callback = callback
        }

        else -> Unit
    }
}

fun interface UsersCalback {
    fun onUserClick(user: User)
}