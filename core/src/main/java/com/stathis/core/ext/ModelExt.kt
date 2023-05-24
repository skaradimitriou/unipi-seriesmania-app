package com.stathis.core.ext

import android.content.Context
import com.stathis.core.R
import com.stathis.domain.model.ResultType

fun ResultType.toDisplayText(context: Context) = when (this) {
    ResultType.TRENDING -> context.resources.getString(R.string.trending_title)
    ResultType.TOP_RATED -> context.resources.getString(R.string.top_rated_title)
    ResultType.AIRING_TODAY -> context.resources.getString(R.string.airing_today_title)
    else -> ""
}