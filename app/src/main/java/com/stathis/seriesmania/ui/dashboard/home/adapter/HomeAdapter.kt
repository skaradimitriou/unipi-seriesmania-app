package com.stathis.seriesmania.ui.dashboard.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.stathis.core.util.decorations.HorizontalItemDecoration
import com.stathis.domain.model.TvSeries
import com.stathis.domain.model.UiModel
import com.stathis.domain.model.dashboard.AiringTodaySeries
import com.stathis.domain.model.dashboard.PopularSeries
import com.stathis.domain.model.dashboard.TopRatedSeries
import com.stathis.domain.model.dashboard.TrendingSeries
import com.stathis.seriesmania.BR
import com.stathis.seriesmania.R
import com.stathis.seriesmania.base.BaseDiffUtil
import com.stathis.seriesmania.base.BaseViewHolder
import com.stathis.seriesmania.databinding.HolderAiringTodaySeriesBinding
import com.stathis.seriesmania.databinding.HolderEmptyViewBinding
import com.stathis.seriesmania.databinding.HolderPopularSeriesBinding
import com.stathis.seriesmania.databinding.HolderTopRatedSeriesBinding
import com.stathis.seriesmania.databinding.HolderTrendingSeriesBinding

class HomeAdapter(private val callback: SeriesCallback) :
    ListAdapter<UiModel, HomeViewHolder>(BaseDiffUtil<UiModel>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = when (viewType) {
            R.layout.holder_popular_series -> {
                HolderPopularSeriesBinding.inflate(inflater, parent, false)
            }

            R.layout.holder_top_rated_series -> {
                HolderTopRatedSeriesBinding.inflate(inflater, parent, false)
            }

            R.layout.holder_trending_series -> {
                HolderTrendingSeriesBinding.inflate(inflater, parent, false)
            }

            R.layout.holder_airing_today_series -> {
                HolderAiringTodaySeriesBinding.inflate(inflater, parent, false)
            }

            else -> HolderEmptyViewBinding.inflate(inflater, parent, false)
        }
        return HomeViewHolder(view, callback)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is PopularSeries -> R.layout.holder_popular_series
        is TopRatedSeries -> R.layout.holder_top_rated_series
        is TrendingSeries -> R.layout.holder_trending_series
        is AiringTodaySeries -> R.layout.holder_airing_today_series
        else -> R.layout.holder_empty_view
    }
}

class HomeViewHolder(
    private val binding: ViewDataBinding,
    private val callback: SeriesCallback
) : BaseViewHolder(binding) {

    override fun present(data: UiModel) {
        when (data) {
            is PopularSeries -> {
                val adapter = PopularSeriesAdapter(callback)
                adapter.submitList(data.results)
                binding.setVariable(BR.adapter, adapter)
            }

            is TopRatedSeries -> {
                val adapter = TopRatedSeriesAdapter(callback)
                val decor = HorizontalItemDecoration(20)
                adapter.submitList(data.results)
                binding.setVariable(BR.adapter, adapter)
                binding.setVariable(BR.decoration, decor)
            }

            is TrendingSeries -> {
                val adapter = TrendingSeriesAdapter(callback)
                val decor = HorizontalItemDecoration(20)
                adapter.submitList(data.results)
                binding.setVariable(BR.adapter, adapter)
                binding.setVariable(BR.decoration, decor)
            }

            is AiringTodaySeries -> {
                val adapter = AiringTodaySeriesAdapter(callback)
                val decor = HorizontalItemDecoration(20)
                adapter.submitList(data.results)
                binding.setVariable(BR.adapter, adapter)
                binding.setVariable(BR.decoration, decor)
            }
        }
    }
}

fun interface SeriesCallback {
    fun onSeriesClick(model: TvSeries)
}