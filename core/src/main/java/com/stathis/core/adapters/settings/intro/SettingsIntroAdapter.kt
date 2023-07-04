package com.stathis.core.adapters.settings.intro

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.stathis.core.base.BaseDiffUtil
import com.stathis.core.base.BaseViewHolder
import com.stathis.core.databinding.HolderSettingIntroItemBinding
import com.stathis.domain.model.UiModel

class SettingsIntroAdapter(
    val callback: SettingsIntroCallback
) : ListAdapter<UiModel, SettingsIntroViewHolder>(BaseDiffUtil<UiModel>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsIntroViewHolder {
        val view = HolderSettingIntroItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SettingsIntroViewHolder(view, callback)
    }

    override fun onBindViewHolder(holder: SettingsIntroViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class SettingsIntroViewHolder(
    val binding: HolderSettingIntroItemBinding,
    val callback: SettingsIntroCallback
) :
    BaseViewHolder(binding) {

    override fun present(data: UiModel) = when (data) {
        is SettingsIntroItem -> {
            binding.model = data
            binding.callback = callback
        }

        else -> Unit
    }
}

fun interface SettingsIntroCallback {
    fun onItemClick(item: SettingsIntroItem)
}

data class SettingsIntroItem(
    val title: String,
    val type: SettingsIntroType
) : UiModel {
    override fun equalsContent(obj: UiModel): Boolean = when (obj) {
        is SettingsIntroItem -> title == obj.title && type == obj.type
        else -> false
    }
}

enum class SettingsIntroType {
    ANALYTICS,
    FAQ,
    ABOUT
}