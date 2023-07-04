package com.stathis.domain.model.profile

import android.os.Parcelable
import com.stathis.domain.model.UiModel
import com.stathis.domain.model.profile.uimodel.SeriesPreference
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var id: String,
    val username: String,
    val bio: String,
    val preferences: List<SeriesPreference>,
    val email: String,
    val userImg: String
) : UiModel, Parcelable {
    @IgnoredOnParcel
    var following: Boolean = false

    constructor() : this("", "", "", listOf(), "", "")

    override fun equalsContent(obj: UiModel): Boolean = when (obj) {
        is User -> userImg == obj.userImg && username == obj.username && following == obj.following
        else -> false
    }
}