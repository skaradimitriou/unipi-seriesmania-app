package com.stathis.core.adapters.general

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.stathis.core.base.BaseDiffUtil
import com.stathis.core.base.BaseViewHolder
import com.stathis.core.databinding.HolderSeriesDetailItemBinding
import com.stathis.domain.model.TvSeriesDetails
import com.stathis.domain.model.UiModel

class SeriesDetailsAdapter :
    ListAdapter<UiModel, SeriesDetailsViewHolder>(BaseDiffUtil<UiModel>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesDetailsViewHolder {
        val view = HolderSeriesDetailItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SeriesDetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: SeriesDetailsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class SeriesDetailsViewHolder(
    private val binding: HolderSeriesDetailItemBinding
) : BaseViewHolder(binding) {

    override fun present(data: UiModel) = when (data) {
        is TvSeriesDetails -> {
            binding.model = data
        }

        else -> Unit
    }
}