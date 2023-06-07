package com.stathis.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.stathis.core.ext.toListOf
import com.stathis.core.util.auth.Authenticator
import com.stathis.core.util.session.SessionManager
import com.stathis.data.mappers.SingleThreadMapper
import com.stathis.data.mappers.ThreadMapper
import com.stathis.data.model.ThreadDto
import com.stathis.data.util.FORUM_DB_PATH
import com.stathis.domain.model.forum.Thread
import com.stathis.domain.model.forum.ThreadReply
import com.stathis.domain.repositories.ForumRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ForumRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: Authenticator,
    private val sessionManager: SessionManager
) : ForumRepository {

    override suspend fun fetchAllThreads(): List<Thread> {
        val result = firestore.collection(FORUM_DB_PATH)
            .get()
            .await()
            .toListOf<ThreadDto>()

        return ThreadMapper.toDomainModel(result)
    }

    override suspend fun fetchThreadById(threadId: String): Thread {
        val model = firestore.collection(FORUM_DB_PATH).document(threadId).snapshots().firstOrNull()
        val dto = model?.toObject(ThreadDto::class.java)
        return SingleThreadMapper.toDomainModel(dto)
    }

    override suspend fun addNewThread(title: String, body: String): Boolean {
        firestore.collection(FORUM_DB_PATH).document().apply {
            val data: HashMap<String, Any> = hashMapOf(
                "threadId" to id,
                "title" to title,
                "body" to body,
                "user" to sessionManager.retrieveUserData().first()
            )

            set(data).await()
        }

        return true
    }

    override suspend fun addNewReply(reply: String, thread: Thread): Boolean {
        val user = sessionManager.retrieveUserData().first()
        val replyModel = ThreadReply(reply, user)

        val replies = thread.replies.toMutableList().apply {
            add(replyModel)
        }

        firestore.collection(FORUM_DB_PATH)
            .document(thread.threadId)
            .update(mapOf("replies" to replies))
            .await()

        return true
    }
}