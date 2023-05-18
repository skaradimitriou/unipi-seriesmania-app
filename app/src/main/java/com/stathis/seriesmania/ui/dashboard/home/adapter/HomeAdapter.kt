package com.stathis.seriesmania.ui.dashboard.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.stathis.domain.model.UiModel
import com.stathis.domain.model.dashboard.AiringTodaySeries
import com.stathis.domain.model.dashboard.OnTheAirSeries
import com.stathis.domain.model.dashboard.PopularSeries
import com.stathis.domain.model.dashboard.TopRatedSeries
import com.stathis.seriesmania.BR
import com.stathis.seriesmania.R
import com.stathis.seriesmania.base.BaseDiffUtil
import com.stathis.seriesmania.base.BaseViewHolder
import com.stathis.seriesmania.databinding.HolderEmptyViewBinding
import com.stathis.seriesmania.databinding.HolderPopularSeriesBinding

class HomeAdapter() : ListAdapter<UiModel, HomeViewHolder>(BaseDiffUtil<UiModel>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = when (viewType) {
            R.layout.holder_popular_series -> {
                HolderPopularSeriesBinding.inflate(inflater, parent, false)
            }

            else -> {
                HolderEmptyViewBinding.inflate(inflater, parent, false)
            }
        }
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is PopularSeries -> R.layout.holder_popular_series
        //is TopRatedSeries -> 1
        //is OnTheAirSeries -> 2
        //is AiringTodaySeries -> 3
        else -> R.layout.holder_empty_view
    }
}

class HomeViewHolder(val binding: ViewDataBinding) : BaseViewHolder(binding) {

    override fun present(data: UiModel) {
        when (data) {
            is PopularSeries -> {

                binding.setVariable(BR.adapter, adapter)
            }

            is TopRatedSeries -> {
                //
            }

            is OnTheAirSeries -> {
                //
            }

            is AiringTodaySeries -> {
                //
            }
        }
    }
}