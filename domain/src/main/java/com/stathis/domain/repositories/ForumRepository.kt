package com.stathis.domain.repositories

import com.stathis.domain.model.forum.Thread

interface ForumRepository {

    suspend fun fetchAllThreads(): List<Thread>
}