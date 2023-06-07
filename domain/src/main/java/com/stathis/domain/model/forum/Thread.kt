package com.stathis.domain.model.forum

import android.os.Parcelable
import com.stathis.domain.model.UiModel
import com.stathis.domain.model.profile.User
import kotlinx.parcelize.Parcelize

@Parcelize
data class Thread(
    val title: String,
    val body: String,
    val user: User,
    val threadId: String,
    val replies: List<ThreadReply>
) : UiModel, Parcelable {
    override fun equalsContent(obj: UiModel): Boolean = when (obj) {
        is Thread -> true
        else -> false
    }
}