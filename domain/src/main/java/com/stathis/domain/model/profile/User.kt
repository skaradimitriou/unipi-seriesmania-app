package com.stathis.domain.model.profile

import android.os.Parcelable
import com.stathis.domain.model.UiModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val firstName: String,
    val lastName: String,
    val telephone: String,
    val email: String,
    val userImg: String,
    val username: String,
) : UiModel, Parcelable {
    override fun equalsContent(obj: UiModel): Boolean = when (obj) {
        is User -> userImg == obj.userImg && username == obj.username
        else -> false
    }
}