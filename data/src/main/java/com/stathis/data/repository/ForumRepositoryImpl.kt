package com.stathis.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.stathis.core.ext.toListOf
import com.stathis.core.ext.toNotNull
import com.stathis.core.util.auth.Authenticator
import com.stathis.data.mappers.SingleThreadMapper
import com.stathis.data.mappers.ThreadMapper
import com.stathis.data.model.ForumThreadDto
import com.stathis.data.model.ThreadReplyDto
import com.stathis.data.util.FORUM_DB_PATH
import com.stathis.domain.model.forum.ForumThread
import com.stathis.domain.model.forum.ThreadReply
import com.stathis.domain.repositories.ForumRepository
import com.stathis.domain.repositories.ProfileRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ForumRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: Authenticator,
    private val profileRepository: ProfileRepository
) : ForumRepository {

    override suspend fun fetchAllThreads(): List<ForumThread> {
        val result = firestore.collection(FORUM_DB_PATH)
            .get()
            .await()
            .toListOf<ForumThreadDto>()

        val data = ThreadMapper.toDomainModel(result).apply {
            map {
                if (it.user.id.isNotEmpty()) {
                    it.user = profileRepository.getUserInfo(it.user.id.toNotNull())
                }
            }
        }

        return data
    }

    override suspend fun fetchThreadById(threadId: String): ForumThread {
        val model = firestore.collection(FORUM_DB_PATH)
            .document(threadId)
            .snapshots()
            .firstOrNull()

        val dto = model?.toObject(ForumThreadDto::class.java)
        val mappedData = SingleThreadMapper.toDomainModel(dto).apply {
            if (user.id.isNotEmpty()) {
                user = profileRepository.getUserInfo(user.id.toNotNull())
            }

            replies = replies.map {
                ThreadReply(
                    message = it.message,
                    user = profileRepository.getUserInfo(it.user.id.toNotNull())
                )
            }
        }

        return mappedData
    }

    override suspend fun addNewThread(title: String, body: String): Boolean {
        firestore.collection(FORUM_DB_PATH).document().apply {
            val data: HashMap<String, Any> = hashMapOf(
                "threadId" to id,
                "title" to title,
                "body" to body,
                "userId" to auth.getActiveUserId()
            )

            set(data).await()
        }

        return true
    }

    override suspend fun addNewReply(reply: String, forumThread: ForumThread): Boolean {
        val replyModel = ThreadReplyDto(reply, auth.getActiveUserId())

        val model = firestore.collection(FORUM_DB_PATH)
            .document(forumThread.threadId)
            .snapshots()
            .firstOrNull()

        val oldReplies = model?.toObject(ForumThreadDto::class.java)?.replies

        val replies = mutableListOf<Any>().apply {
            oldReplies?.let { addAll(it) }
            add(replyModel)
        }

        firestore.collection(FORUM_DB_PATH)
            .document(forumThread.threadId)
            .update(mapOf("replies" to replies))
            .await()

        return true
    }
}