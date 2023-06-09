package com.stathis.core.adapters.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.stathis.core.BR
import com.stathis.core.R
import com.stathis.core.base.BaseDiffUtil
import com.stathis.core.base.BaseViewHolder
import com.stathis.core.databinding.HolderEmptyViewBinding
import com.stathis.core.databinding.HolderUserItemBinding
import com.stathis.domain.model.UiModel
import com.stathis.domain.model.profile.User

class UserProfileAdapter : ListAdapter<UiModel, UserProfileViewHolder>(BaseDiffUtil<UiModel>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserProfileViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = when (viewType) {
            R.layout.holder_user_item -> HolderUserItemBinding.inflate(inflater, parent, false)
            else -> HolderEmptyViewBinding.inflate(inflater, parent, false)
        }
        return UserProfileViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserProfileViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int) = when (getItem(position)) {
        is User -> R.layout.holder_user_item
        else -> R.layout.holder_empty_view
    }
}

class UserProfileViewHolder(
    private val binding: ViewDataBinding
) : BaseViewHolder(binding) {

    override fun present(data: UiModel) {
        when (data) {
            is User -> {
                binding.setVariable(BR.model, data)
            }
        }
    }
}

interface UserProfileCallback {
    fun onFollowClick(model: User)
    fun onSeriesClick()
}