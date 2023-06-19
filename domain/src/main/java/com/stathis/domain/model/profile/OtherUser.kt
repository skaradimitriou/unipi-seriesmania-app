package com.stathis.domain.model.profile

import android.os.Parcelable
import com.stathis.domain.model.UiModel
import com.stathis.domain.model.profile.uimodel.SeriesPreference
import kotlinx.parcelize.Parcelize

@Parcelize
data class OtherUser(
    val id: String,
    val username: String,
    val bio: String,
    val preferences: List<SeriesPreference>,
    val email: String,
    val userImg: String
) : Parcelable, UiModel {
    constructor() : this("", "", "", listOf(), "", "")

    override fun equalsContent(obj: UiModel): Boolean = when (obj) {
        is User -> userImg == obj.userImg && username == obj.username
        else -> false
    }
}
