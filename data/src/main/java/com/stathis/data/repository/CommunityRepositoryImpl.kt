package com.stathis.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.stathis.core.ext.toListOf
import com.stathis.core.util.auth.Authenticator
import com.stathis.data.mappers.FollowsMapper
import com.stathis.data.util.FOLLOWS_DB_PATH
import com.stathis.domain.model.follows.FollowsWrapper
import com.stathis.domain.model.profile.OtherUser
import com.stathis.domain.model.profile.toOtherUser
import com.stathis.domain.repositories.CommunityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CommunityRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: Authenticator
) : CommunityRepository {

    override suspend fun follow(user: OtherUser) {
        val list = getMyFollowers().first().toMutableList().apply { add(0, user) }
        val data = mapOf("follows" to list)
        val uuid = auth.getActiveUserId()
        firestore.collection(FOLLOWS_DB_PATH).document(uuid).set(data).await()
    }

    override suspend fun unfollow(user: OtherUser) {
        val list = getMyFollowers().first().toMutableList().apply { remove(user) }
        val data = mapOf("follows" to list)
        val uuid = auth.getActiveUserId()
        firestore.collection(FOLLOWS_DB_PATH).document(uuid).set(data).await()
    }

    override suspend fun getMyFollowers(): Flow<List<OtherUser>> = flow {
        val result = firestore.collection(FOLLOWS_DB_PATH)
            .document(auth.getActiveUserId())
            .get()
            .await()
            .toObject(FollowsWrapper::class.java)

        val data = FollowsMapper.toDomainModel(result)
        emit(data)
    }

    override suspend fun getWhoFollowsMe(): Flow<List<OtherUser>> = flow {
        val result = firestore.collection(FOLLOWS_DB_PATH)
            .get()
            .await()
            .toListOf<FollowsWrapper>()

        val data = result.map {
            it.follows.filter { user -> user.id == auth.getActiveUserId() }.map {
                it.toOtherUser()
            }
        }.flatten()

        emit(data)
    }

    override suspend fun isUserFollowedByMe(userId: String): Boolean {
        return getMyFollowers().last().any { it.id == userId }
    }
}
