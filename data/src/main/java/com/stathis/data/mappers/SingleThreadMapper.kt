package com.stathis.data.mappers

import com.stathis.core.ext.toListOf
import com.stathis.core.ext.toNotNull
import com.stathis.data.model.ThreadDto
import com.stathis.data.model.ThreadReplyDto
import com.stathis.domain.model.forum.Thread
import com.stathis.domain.model.forum.ThreadReply

object SingleThreadMapper : BaseMapper<ThreadDto?, Thread> {

    override fun toDomainModel(dto: ThreadDto?): Thread = Thread(
        title = dto?.title.toNotNull(),
        body = dto?.body.toNotNull(),
        user = UserMapper.toDomainModel(dto?.user),
        threadId = dto?.threadId.toNotNull(),
        replies = dto?.replies.toThreadReplies()
    )


    private fun List<ThreadReplyDto>?.toThreadReplies() = toListOf {
        ThreadReply(
            message = it.message.toNotNull(),
            user = UserMapper.toDomainModel(it.user)
        )
    }
}