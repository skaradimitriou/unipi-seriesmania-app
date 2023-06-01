package com.stathis.seriesmania.ui.dashboard.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.stathis.core.base.BaseDiffUtil
import com.stathis.core.base.BaseViewHolder
import com.stathis.domain.model.UiModel
import com.stathis.seriesmania.BR
import com.stathis.seriesmania.R
import com.stathis.seriesmania.databinding.HolderEmptyViewBinding
import com.stathis.seriesmania.databinding.HolderLogoutOptionBinding
import com.stathis.seriesmania.databinding.HolderProfileHeaderBinding
import com.stathis.seriesmania.databinding.HolderProfileOptionItemBinding
import com.stathis.seriesmania.ui.dashboard.profile.uimodel.ProfileHeader
import com.stathis.seriesmania.ui.dashboard.profile.uimodel.ProfileLogoutOption
import com.stathis.seriesmania.ui.dashboard.profile.uimodel.ProfileOption

class ProfileAdapter(
    private val callback: ProfileScreenCallback
) : ListAdapter<UiModel, ProfileOptionsViewHolder>(BaseDiffUtil<UiModel>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileOptionsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = when (viewType) {
            R.layout.holder_profile_header -> {
                HolderProfileHeaderBinding.inflate(inflater, parent, false)
            }

            R.layout.holder_profile_option_item -> {
                HolderProfileOptionItemBinding.inflate(inflater, parent, false)
            }

            R.layout.holder_logout_option -> {
                HolderLogoutOptionBinding.inflate(inflater, parent, false)
            }

            else -> HolderEmptyViewBinding.inflate(inflater, parent, false)
        }
        return ProfileOptionsViewHolder(view, callback)
    }

    override fun onBindViewHolder(holder: ProfileOptionsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is ProfileHeader -> R.layout.holder_profile_header
        is ProfileOption -> R.layout.holder_profile_option_item
        is ProfileLogoutOption -> R.layout.holder_logout_option
        else -> R.layout.holder_empty_view
    }
}

class ProfileOptionsViewHolder(
    private val binding: ViewDataBinding, private val callback: ProfileScreenCallback
) : BaseViewHolder(binding) {

    override fun present(data: UiModel) {
        when (data) {
            is ProfileHeader -> binding.setVariable(BR.model, data)
            is ProfileOption -> binding.setVariable(BR.model, data)
            is ProfileLogoutOption -> {
                binding.setVariable(BR.model, data)
                binding.setVariable(BR.callback, callback)
            }
        }
    }
}

fun interface ProfileScreenCallback {
    fun onLogoutClick()
}