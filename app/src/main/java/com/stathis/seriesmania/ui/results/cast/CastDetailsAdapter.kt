package com.stathis.seriesmania.ui.results.cast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.stathis.core.adapters.general.SeriesCallback
import com.stathis.core.base.BaseDiffUtil
import com.stathis.core.base.BaseViewHolder
import com.stathis.core.util.decorations.VerticalItemDecoration
import com.stathis.domain.model.TvSeriesWrapper
import com.stathis.domain.model.UiModel
import com.stathis.domain.model.cast.ActorDetails
import com.stathis.seriesmania.BR
import com.stathis.seriesmania.R
import com.stathis.seriesmania.databinding.HolderActorItemBinding
import com.stathis.seriesmania.databinding.HolderEmptyViewBinding
import com.stathis.seriesmania.databinding.HolderKnownForItemBinding

class CastDetailsAdapter(
    private val callback: SeriesCallback
) : ListAdapter<UiModel, CastDetailsViewHolder>(BaseDiffUtil<UiModel>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastDetailsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = when (viewType) {
            R.layout.holder_actor_item -> {
                HolderActorItemBinding.inflate(inflater, parent, false)
            }

            R.layout.holder_known_for_item -> {
                HolderKnownForItemBinding.inflate(inflater, parent, false)
            }

            else -> HolderEmptyViewBinding.inflate(inflater, parent, false)
        }
        return CastDetailsViewHolder(view, callback)
    }

    override fun onBindViewHolder(holder: CastDetailsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is ActorDetails -> R.layout.holder_actor_item
        is TvSeriesWrapper -> R.layout.holder_known_for_item
        else -> R.layout.holder_empty_view
    }
}

class CastDetailsViewHolder(
    private val binding: ViewDataBinding,
    private val callback: SeriesCallback
) : BaseViewHolder(binding) {

    override fun present(data: UiModel) {
        when (data) {
            is ActorDetails -> {
                binding.setVariable(BR.model, data)
            }

            is TvSeriesWrapper -> {
                val decor = VerticalItemDecoration(30)
                val adapter = KnownForAdapter(callback)
                adapter.submitList(data.series)
                binding.setVariable(BR.adapter, adapter)
                binding.setVariable(BR.decoration, decor)
            }
        }
    }
}