package com.stathis.core.adapters.general

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.stathis.core.base.BaseDiffUtil
import com.stathis.core.base.BaseViewHolder
import com.stathis.core.databinding.HolderAiringTodayItemBinding
import com.stathis.domain.model.TvSeries
import com.stathis.domain.model.UiModel

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

fun interface SeriesCallback {
    fun onSeriesClick(model: TvSeries)
}