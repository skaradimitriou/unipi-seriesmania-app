package com.stathis.domain.model.profile

import android.os.Parcelable
import com.stathis.domain.model.UiModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserPreferences(
    val preferences: List<String>
) : Parcelable, UiModel {
    override fun equalsContent(obj: UiModel): Boolean = when (obj) {
        is UserPreferences -> preferences == obj.preferences
        else -> false
    }
}