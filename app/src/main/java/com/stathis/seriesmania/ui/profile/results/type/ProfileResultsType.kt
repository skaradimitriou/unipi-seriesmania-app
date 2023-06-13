package com.stathis.seriesmania.ui.profile.results.type

import android.content.Context
import java.io.Serializable

enum class ProfileResultsType : Serializable {
    FOLLOWING,
    FOLLOWERS,
    WATCHLIST
}

fun ProfileResultsType.toScreenTitle(context: Context) = when (this) {
    ProfileResultsType.FOLLOWING -> context.getString(com.stathis.core.R.string.following)
    ProfileResultsType.FOLLOWERS -> context.getString(com.stathis.core.R.string.followers)
    ProfileResultsType.WATCHLIST -> context.getString(com.stathis.core.R.string.watchlist)
}