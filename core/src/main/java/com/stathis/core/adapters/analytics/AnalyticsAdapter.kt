package com.stathis.core.adapters.analytics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.stathis.core.BR
import com.stathis.core.adapters.analytics.uimodel.AnalyticsDetail
import com.stathis.core.base.BaseDiffUtil
import com.stathis.core.base.BaseViewHolder
import com.stathis.core.databinding.HolderAnalyticsDetailBinding
import com.stathis.domain.model.UiModel

class AnalyticsAdapter(
    private val callback: AnalyticsCallback
) : ListAdapter<UiModel, AnalyticsViewHolder>(BaseDiffUtil<UiModel>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnalyticsViewHolder {
        val view = HolderAnalyticsDetailBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return AnalyticsViewHolder(view, callback)
    }

    override fun onBindViewHolder(holder: AnalyticsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class AnalyticsViewHolder(
    private val binding: ViewDataBinding,
    private val callback: AnalyticsCallback
) : BaseViewHolder(binding) {

    override fun present(data: UiModel) {
        when (data) {
            is AnalyticsDetail -> {
                binding.setVariable(BR.model, data)
                binding.setVariable(BR.callback, callback)
            }
        }
    }
}

interface AnalyticsCallback {
    fun onUserClick()
    fun onSeriesClick()
}