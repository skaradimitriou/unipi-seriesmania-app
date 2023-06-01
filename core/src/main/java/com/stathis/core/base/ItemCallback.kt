package com.stathis.core.base

import com.stathis.domain.model.UiModel

interface ItemCallback {
    fun onItemClick(model: UiModel)
}