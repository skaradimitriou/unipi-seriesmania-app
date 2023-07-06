package com.stathis.core.adapters.analytics.uimodel

import com.stathis.domain.model.UiModel

data class AnalyticsPreferencesItem(
    val string: String
) : UiModel {
    override fun equalsContent(obj: UiModel) = when (obj) {
        is AnalyticsPreferencesItem -> string == obj.string

        else -> false
    }
}