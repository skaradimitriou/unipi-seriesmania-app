package com.stathis.seriesmania.ui.dashboard.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.stathis.core.R
import com.stathis.core.adapters.general.AiringTodaySeriesAdapter
import com.stathis.core.adapters.general.PopularSeriesAdapter
import com.stathis.core.adapters.general.SeriesCallback
import com.stathis.core.adapters.general.TopRatedSeriesAdapter
import com.stathis.core.adapters.general.TrendingSeriesAdapter
import com.stathis.core.base.BaseDiffUtil
import com.stathis.core.base.BaseViewHolder
import com.stathis.core.callbacks.DashboardCallback
import com.stathis.core.databinding.HolderAiringTodaySeriesBinding
import com.stathis.core.databinding.HolderDashboardUserBinding
import com.stathis.core.databinding.HolderEmptyViewBinding
import com.stathis.core.databinding.HolderPopularSeriesBinding
import com.stathis.core.databinding.HolderSeriesWrapperItemBinding
import com.stathis.core.databinding.HolderTopRatedSeriesBinding
import com.stathis.core.databinding.HolderTrendingSeriesBinding
import com.stathis.core.util.decorations.HorizontalItemDecoration
import com.stathis.domain.model.TvSeries
import com.stathis.domain.model.TvSeriesWrapper
import com.stathis.domain.model.UiModel
import com.stathis.domain.model.dashboard.AiringTodaySeries
import com.stathis.domain.model.dashboard.PopularSeries
import com.stathis.domain.model.dashboard.TopRatedSeries
import com.stathis.domain.model.dashboard.TrendingSeries
import com.stathis.domain.model.profile.User
import com.stathis.seriesmania.BR

class HomeAdapter(
    private val callback: DashboardCallback
) : ListAdapter<UiModel, HomeViewHolder>(BaseDiffUtil<UiModel>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = when (viewType) {
            R.layout.holder_dashboard_user -> {
                HolderDashboardUserBinding.inflate(inflater, parent, false)
            }

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

            R.layout.holder_series_wrapper_item -> {
                HolderSeriesWrapperItemBinding.inflate(inflater, parent, false)
            }

            else -> HolderEmptyViewBinding.inflate(inflater, parent, false)
        }
        return HomeViewHolder(view, callback)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is User -> R.layout.holder_dashboard_user
        is PopularSeries -> R.layout.holder_popular_series
        is TopRatedSeries -> R.layout.holder_top_rated_series
        is TrendingSeries -> R.layout.holder_trending_series
        is AiringTodaySeries -> R.layout.holder_airing_today_series
        is TvSeriesWrapper -> R.layout.holder_series_wrapper_item
        else -> R.layout.holder_empty_view
    }
}

class HomeViewHolder(
    private val binding: ViewDataBinding,
    private val callback: DashboardCallback
) : BaseViewHolder(binding), SeriesCallback {

    override fun present(data: UiModel) {
        when (data) {
            is User -> {
                binding.setVariable(BR.model, data)
                binding.setVariable(BR.callback, callback)
            }

            is PopularSeries -> {
                val adapter = PopularSeriesAdapter(this)
                adapter.submitList(data.results)
                binding.setVariable(BR.adapter, adapter)
            }

            is TopRatedSeries -> {
                val adapter = TopRatedSeriesAdapter(this)
                val decor = HorizontalItemDecoration(20)
                adapter.submitList(data.results)
                binding.setVariable(BR.adapter, adapter)
                binding.setVariable(BR.decoration, decor)
                binding.setVariable(BR.callback, callback)
            }

            is TrendingSeries -> {
                val adapter = TrendingSeriesAdapter(this)
                val decor = HorizontalItemDecoration(20)
                adapter.submitList(data.results)
                binding.setVariable(BR.adapter, adapter)
                binding.setVariable(BR.decoration, decor)
                binding.setVariable(BR.callback, callback)
            }

            is AiringTodaySeries -> {
                val adapter = AiringTodaySeriesAdapter(this)
                val decor = HorizontalItemDecoration(20)
                adapter.submitList(data.results)
                binding.setVariable(BR.adapter, adapter)
                binding.setVariable(BR.decoration, decor)
                binding.setVariable(BR.callback, callback)
            }

            is TvSeriesWrapper -> {
                val adapter = AiringTodaySeriesAdapter(this)
                val decor = HorizontalItemDecoration(20)
                adapter.submitList(data.series)
                binding.setVariable(BR.adapter, adapter)
                binding.setVariable(BR.decoration, decor)
                binding.setVariable(BR.model, data)
            }
        }
    }

    override fun onSeriesClick(model: TvSeries) {
        callback.onSeriesClick(model)
    }
}