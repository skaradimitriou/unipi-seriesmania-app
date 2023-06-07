package com.stathis.domain.model.forum

import android.os.Parcelable
import com.stathis.domain.model.UiModel
import com.stathis.domain.model.profile.User
import kotlinx.parcelize.Parcelize

@Parcelize
data class ThreadReply(
    val message: String,
    val user: User
) : Parcelable, UiModel {
    override fun equalsContent(obj: UiModel): Boolean = when (obj) {
        is ThreadReply -> message == obj.message && user == obj.user
        else -> false
    }
}
