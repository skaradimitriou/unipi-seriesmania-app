package com.stathis.core.adapters.analytics.uimodel

import com.stathis.domain.model.UiModel

data class AnalyticsDetail(
    val title: String,
    val value: String
) : UiModel {
    override fun equalsContent(obj: UiModel) = when (obj) {
        is AnalyticsDetail -> title == obj.title && value == obj.value
        else -> false
    }
}