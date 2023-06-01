package com.stathis.seriesmania.ui.dashboard.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.stathis.core.base.BaseDiffUtil
import com.stathis.core.base.BaseViewHolder
import com.stathis.domain.model.TvSeries
import com.stathis.domain.model.UiModel
import com.stathis.seriesmania.databinding.HolderOntheairItemBinding

class TrendingSeriesAdapter(
    private val callback: SeriesCallback
) : ListAdapter<UiModel, TrendingSeriesViewHolder>(BaseDiffUtil<UiModel>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingSeriesViewHolder {
        val view = HolderOntheairItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TrendingSeriesViewHolder(view, callback)
    }

    override fun onBindViewHolder(holder: TrendingSeriesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class TrendingSeriesViewHolder(
    private val binding: HolderOntheairItemBinding,
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