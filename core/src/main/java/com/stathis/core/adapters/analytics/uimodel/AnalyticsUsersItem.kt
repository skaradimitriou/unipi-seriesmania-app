package com.stathis.core.adapters.analytics.uimodel

import com.stathis.domain.model.UiModel
import com.stathis.domain.model.profile.User

data class AnalyticsUsersItem(
    val users: List<AnalyticsUsersChildItem>
) : UiModel {
    override fun equalsContent(obj: UiModel) = when (obj) {
        is AnalyticsUsersItem -> users == obj.users
        else -> false
    }
}

data class AnalyticsUsersChildItem(
    val title: String,
    val user: User
) : UiModel {
    override fun equalsContent(obj: UiModel) = when (obj) {
        is AnalyticsUsersChildItem -> title == obj.title && user == obj.user
        else -> false
    }
}
