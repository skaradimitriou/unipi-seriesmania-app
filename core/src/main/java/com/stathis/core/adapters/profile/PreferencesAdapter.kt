package com.stathis.seriesmania.ui.profile.preferences.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.stathis.core.base.BaseDiffUtil
import com.stathis.core.base.BaseViewHolder
import com.stathis.core.databinding.HolderPreferenceItemBinding
import com.stathis.domain.model.UiModel
import com.stathis.domain.model.profile.uimodel.SeriesPreference

class PreferencesAdapter(
    private val callback: PreferencesCallback
) : ListAdapter<UiModel, PreferencesViewHolder>(BaseDiffUtil<UiModel>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreferencesViewHolder {
        val view =
            HolderPreferenceItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PreferencesViewHolder(view, callback)
    }

    override fun onBindViewHolder(holder: PreferencesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class PreferencesViewHolder(
    private val binding: HolderPreferenceItemBinding,
    private val callback: PreferencesCallback
) : BaseViewHolder(binding) {

    override fun present(data: UiModel) {
        when (data) {
            is SeriesPreference -> {
                binding.model = data

                binding.mainConstraint.setOnClickListener {
                    data.selected = !data.selected
                    binding.model = data
                    callback.onPreferencesClick()
                }
            }
        }
    }
}

fun interface PreferencesCallback {
    fun onPreferencesClick()
}