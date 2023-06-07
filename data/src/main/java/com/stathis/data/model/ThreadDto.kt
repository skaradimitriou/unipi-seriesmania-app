package com.stathis.data.model

data class ThreadDto(
    val title: String? = null,
    val body: String? = null,
    val user: UserDto? = null,
    val threadId: String? = null,
    val replies: List<ThreadReplyDto>? = null
)

data class ThreadReplyDto(
    val message: String? = null,
    val user: UserDto? = null
)