package com.stathis.core.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.stathis.domain.model.UiModel

abstract class BaseViewHolder(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: UiModel) {
        present(data)
    }

    abstract fun present(data: UiModel)
}