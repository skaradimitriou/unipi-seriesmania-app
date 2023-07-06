package com.stathis.core.adapters.analytics.uimodel

import com.stathis.domain.model.UiModel
import com.stathis.domain.model.forum.ForumThread

data class AnalyticsThreadItem(
    val thread: ForumThread
) : UiModel {
    override fun equalsContent(obj: UiModel) = when (obj) {
        is AnalyticsThreadItem -> thread == obj.thread
        else -> false
    }
}
