package com.stathis.seriesmania.ui.results.cast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.stathis.domain.model.TvSeries
import com.stathis.domain.model.UiModel
import com.stathis.seriesmania.base.BaseDiffUtil
import com.stathis.seriesmania.base.BaseViewHolder
import com.stathis.seriesmania.databinding.HolderTvSeriesItemBinding
import com.stathis.seriesmania.ui.dashboard.home.adapter.SeriesCallback

class KnownForAdapter(
    private val callback: SeriesCallback
) : ListAdapter<UiModel, KnownForViewHolder>(BaseDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KnownForViewHolder {
        val view = HolderTvSeriesItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return KnownForViewHolder(view, callback)
    }

    override fun onBindViewHolder(holder: KnownForViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}

class KnownForViewHolder(
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