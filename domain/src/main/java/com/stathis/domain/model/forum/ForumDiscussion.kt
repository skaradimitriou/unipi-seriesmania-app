package com.stathis.domain.model.forum

import android.os.Parcelable
import com.stathis.domain.model.UiModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class ForumDiscussion(
    val title: String,
    val body: String,
    val userId: String,
    val threadId: String,
    val replies: List<ThreadReply>
) : UiModel, Parcelable {
    override fun equalsContent(obj: UiModel): Boolean = when (obj) {
        is ForumDiscussion -> threadId == obj.threadId && title == obj.title
                && body == obj.body && replies == obj.replies
        else -> false
    }
}
