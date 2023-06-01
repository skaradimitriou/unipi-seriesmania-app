package com.stathis.seriesmania.ui.results.results

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.stathis.core.base.BaseDiffUtil
import com.stathis.core.base.BaseViewHolder
import com.stathis.domain.model.TvSeries
import com.stathis.domain.model.UiModel
import com.stathis.seriesmania.databinding.HolderTvSeriesItemBinding
import com.stathis.seriesmania.ui.dashboard.home.adapter.SeriesCallback

class ResultsPagingAdapter(
    private val callback: SeriesCallback
) : PagingDataAdapter<UiModel, ResultsPagingViewHolder>(BaseDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultsPagingViewHolder {
        val view = HolderTvSeriesItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ResultsPagingViewHolder(view, callback)
    }

    override fun onBindViewHolder(holder: ResultsPagingViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}

class ResultsPagingViewHolder(
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