package com.stathis.seriesmania.ui.dashboard.home.adapter

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.stathis.domain.model.UiModel
import com.stathis.seriesmania.base.BaseDiffUtil
import com.stathis.seriesmania.base.BaseViewHolder

class OnTheAirSeriesAdapter() :
    ListAdapter<UiModel, OnTheAirSeriesViewHolder>(BaseDiffUtil<UiModel>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnTheAirSeriesViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: OnTheAirSeriesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class OnTheAirSeriesViewHolder(val binding: ViewDataBinding) : BaseViewHolder(binding) {

    override fun present(data: UiModel) {
        //
    }
}