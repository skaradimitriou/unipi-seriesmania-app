package com.stathis.seriesmania.base

import com.stathis.domain.model.UiModel

interface ItemCallback {
    fun onItemClick(model: UiModel)
}