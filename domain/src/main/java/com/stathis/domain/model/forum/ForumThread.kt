package com.stathis.domain.model.forum

import android.os.Parcelable
import com.stathis.domain.model.UiModel
import com.stathis.domain.model.profile.User
import kotlinx.parcelize.Parcelize

@Parcelize
data class ForumThread(
    val title: String,
    val body: String,
    var user: User,
    val threadId: String,
    var replies: List<ThreadReply>
) : UiModel, Parcelable {
    override fun equalsContent(obj: UiModel): Boolean = when (obj) {
        is ForumThread -> threadId == obj.threadId && title == obj.title && body == obj.body && replies == obj.replies
        else -> false
    }
}