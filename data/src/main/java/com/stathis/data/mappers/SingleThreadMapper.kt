package com.stathis.data.mappers

import com.stathis.core.ext.toListOf
import com.stathis.core.ext.toNotNull
import com.stathis.data.model.ForumThreadDto
import com.stathis.data.model.ThreadReplyDto
import com.stathis.domain.model.forum.ForumThread
import com.stathis.domain.model.forum.ThreadReply
import com.stathis.domain.model.profile.User

object SingleThreadMapper : BaseMapper<ForumThreadDto?, ForumThread> {

    override fun toDomainModel(dto: ForumThreadDto?): ForumThread = ForumThread(
        title = dto?.title.toNotNull(),
        body = dto?.body.toNotNull(),
        user = User().apply { id = dto?.userId.toNotNull() },
        threadId = dto?.threadId.toNotNull(),
        replies = dto?.replies.toThreadReplies()
    )


    private fun List<ThreadReplyDto>?.toThreadReplies() = toListOf {
        ThreadReply(
            message = it.message.toNotNull(),
            user = User().apply { id = it.userId.toNotNull() }
        )
    }
}