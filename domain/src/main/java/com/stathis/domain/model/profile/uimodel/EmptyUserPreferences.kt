package com.stathis.domain.model.profile.uimodel

import com.stathis.domain.model.UiModel

class EmptyUserPreferences : UiModel {
    override fun equalsContent(obj: UiModel): Boolean = true
}

data class UserPreferences(
    val prefs: List<SeriesPreference>
) : UiModel {
    override fun equalsContent(obj: UiModel): Boolean = when (obj) {
        is UserPreferences -> prefs == obj.prefs
        else -> false
    }
}

class EmptyWatchlist : UiModel {
    override fun equalsContent(obj: UiModel): Boolean = true
}

class EmptyFollowing : UiModel {
    override fun equalsContent(obj: UiModel): Boolean = true
}

class EmptyFollowers : UiModel {
    override fun equalsContent(obj: UiModel): Boolean = true
}

data class UserStatistics(
    val followers: String,
    val following: String,
    val watchlist: String
) : UiModel {
    override fun equalsContent(obj: UiModel): Boolean = when (obj) {
        is UserStatistics -> followers == obj.followers && following == obj.following && watchlist == obj.watchlist
        else -> false
    }
}