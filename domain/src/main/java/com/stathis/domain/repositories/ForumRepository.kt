package com.stathis.domain.repositories

import com.stathis.domain.model.forum.Thread

interface ForumRepository {

    suspend fun fetchAllThreads(): List<Thread>

    suspend fun fetchThreadById(threadId: String): Thread

    suspend fun addNewThread(title: String, body: String): Boolean

    suspend fun addNewReply(reply: String, thread: Thread): Boolean
}