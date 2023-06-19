package com.stathis.data.model

data class ForumThreadDto(
    val title: String? = null,
    val body: String? = null,
    val userId: String? = null,
    val threadId: String? = null,
    val replies: List<ThreadReplyDto>? = null
)

data class ThreadReplyDto(
    val message: String? = null,
    val userId: String? = null
)