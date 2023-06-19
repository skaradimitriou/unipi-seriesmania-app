package com.stathis.domain.repositories

import com.stathis.domain.model.forum.ForumThread

interface ForumRepository {

    suspend fun fetchAllThreads(): List<ForumThread>

    suspend fun fetchThreadById(threadId: String): ForumThread

    suspend fun addNewThread(title: String, body: String): Boolean

    suspend fun addNewReply(reply: String, forumThread: ForumThread): Boolean
}