package com.stathis.seriesmania.ui.dashboard.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.stathis.core.base.BaseDiffUtil
import com.stathis.core.base.BaseViewHolder
import com.stathis.domain.model.TvSeries
import com.stathis.domain.model.UiModel
import com.stathis.seriesmania.databinding.HolderPopularItemBinding

class PopularSeriesAdapter(
    private val callback: SeriesCallback
) : ListAdapter<UiModel, PopularSeriesViewHolder>(BaseDiffUtil<UiModel>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularSeriesViewHolder {
        val view = HolderPopularItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PopularSeriesViewHolder(view, callback)
    }

    override fun onBindViewHolder(holder: PopularSeriesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class PopularSeriesViewHolder(
    private val binding: HolderPopularItemBinding,
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