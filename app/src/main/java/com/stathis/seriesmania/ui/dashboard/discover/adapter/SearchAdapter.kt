package com.stathis.seriesmania.ui.dashboard.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.stathis.domain.model.TvSeries
import com.stathis.domain.model.UiModel
import com.stathis.seriesmania.base.BaseDiffUtil
import com.stathis.seriesmania.base.BaseViewHolder
import com.stathis.seriesmania.databinding.HolderTvSeriesItemBinding
import com.stathis.seriesmania.ui.dashboard.home.adapter.SeriesCallback

class SearchAdapter(
    private val callback: SeriesCallback
) : ListAdapter<UiModel, SearchViewHolder>(BaseDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = HolderTvSeriesItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SearchViewHolder(view, callback)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class SearchViewHolder(
    private val binding: HolderTvSeriesItemBinding,
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