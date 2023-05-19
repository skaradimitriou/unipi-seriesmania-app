package com.stathis.seriesmania.ui.dashboard.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.stathis.domain.model.TvSeries
import com.stathis.domain.model.UiModel
import com.stathis.seriesmania.base.BaseDiffUtil
import com.stathis.seriesmania.base.BaseViewHolder
import com.stathis.seriesmania.databinding.HolderTopRatedItemBinding

class TopRatedSeriesAdapter(
    private val callback: SeriesCallback
) : ListAdapter<UiModel, TopRatedSeriesViewHolder>(BaseDiffUtil<UiModel>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopRatedSeriesViewHolder {
        val view = HolderTopRatedItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TopRatedSeriesViewHolder(view, callback)
    }

    override fun onBindViewHolder(holder: TopRatedSeriesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class TopRatedSeriesViewHolder(
    private val binding: HolderTopRatedItemBinding,
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