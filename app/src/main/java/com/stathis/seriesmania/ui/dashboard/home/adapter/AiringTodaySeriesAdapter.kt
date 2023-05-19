package com.stathis.seriesmania.ui.dashboard.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.stathis.domain.model.TvSeries
import com.stathis.domain.model.UiModel
import com.stathis.seriesmania.base.BaseDiffUtil
import com.stathis.seriesmania.base.BaseViewHolder
import com.stathis.seriesmania.databinding.HolderAiringTodayItemBinding

class AiringTodaySeriesAdapter(
    private val callback: SeriesCallback
) : ListAdapter<UiModel, AiringTodayViewHolder>(BaseDiffUtil<UiModel>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AiringTodayViewHolder {
        val view = HolderAiringTodayItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return AiringTodayViewHolder(view, callback)
    }

    override fun onBindViewHolder(holder: AiringTodayViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class AiringTodayViewHolder(
    private val binding: HolderAiringTodayItemBinding,
    private val callback: SeriesCallback
) : BaseViewHolder(binding) {

    override fun present(data: UiModel) = when (data) {
        is TvSeries -> {
            binding.model = data
            binding.callback = callback
        }

        else -> Unit
    }
}