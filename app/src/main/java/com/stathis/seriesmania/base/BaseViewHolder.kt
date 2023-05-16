package com.stathis.seriesmania.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: UiModel) {
        present(data)
    }

    abstract fun present(data: UiModel)
}