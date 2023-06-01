package com.stathis.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.stathis.core.ext.toListOf
import com.stathis.data.mappers.ThreadMapper
import com.stathis.data.model.ThreadDto
import com.stathis.data.util.FORUM_DB_PATH
import com.stathis.domain.model.forum.Thread
import com.stathis.domain.repositories.ForumRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ForumRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : ForumRepository {

    override suspend fun fetchAllThreads(): List<Thread> {
        val result = firestore.collection(FORUM_DB_PATH)
            .get()
            .await()
            .toListOf<ThreadDto>()

        return ThreadMapper.toDomainModel(result)
    }
}