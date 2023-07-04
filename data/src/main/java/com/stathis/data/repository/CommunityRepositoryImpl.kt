package com.stathis.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.stathis.core.ext.toNotNull
import com.stathis.core.util.auth.Authenticator
import com.stathis.data.util.FOLLOWS_DB_PATH
import com.stathis.domain.model.follows.FollowsWrapper
import com.stathis.domain.model.profile.User
import com.stathis.domain.repositories.CommunityRepository
import com.stathis.domain.repositories.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CommunityRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: Authenticator,
    private val profileRepository: ProfileRepository
) : CommunityRepository {

    override suspend fun follow(userId: String) {
        val list = getMyFollowers().first().toMutableList().apply { add(0, userId) }
        val data = mapOf("follows" to list)
        val uuid = auth.getActiveUserId()
        firestore.collection(FOLLOWS_DB_PATH).document(uuid).set(data).await()
    }

    override suspend fun unfollow(userId: String) {
        val list = getMyFollowers().first().toMutableList().apply { remove(userId) }
        val data = mapOf("follows" to list)
        val uuid = auth.getActiveUserId()
        firestore.collection(FOLLOWS_DB_PATH).document(uuid).set(data).await()
    }

    override suspend fun getMyFollowers(userId: String?): Flow<List<String>> = flow {
        val result = firestore.collection(FOLLOWS_DB_PATH)
            .document(auth.getActiveUserId())
            .get()
            .await()
            .toObject(FollowsWrapper::class.java)

        val mappedData = result?.follows ?: listOf()
        emit(mappedData)
    }

    override suspend fun getMyFollowersDetails(userId: String?): Flow<List<User>> = flow {
        val result = firestore.collection(FOLLOWS_DB_PATH)
            .document(userId ?: auth.getActiveUserId())
            .get()
            .await()
            .toObject(FollowsWrapper::class.java)

        val mappedData = result?.follows?.map { userId ->
            profileRepository.getUserInfo(userId.toNotNull())
        } ?: listOf()
        emit(mappedData)
    }

    override suspend fun getWhoFollowsMe(userId: String?): Flow<List<User>> = flow {
        val result = firestore.collection(FOLLOWS_DB_PATH)
            .whereArrayContains("follows", userId ?: auth.getActiveUserId())
            .get()
            .await()

        val users = result.map { docId ->
            profileRepository.getUserInfo(docId.id.toNotNull())
        }

        emit(users)
    }

    override suspend fun fetchMyFollowingUsersCount(userId: String?) = flow {
        getMyFollowersDetails(userId).collect {
            emit(it.size)
        }
    }

    override suspend fun fetchMyFollowersCount(userId: String?) = flow {
        getWhoFollowsMe(userId).collect {
            emit(it.size)
        }
    }

    override suspend fun fetchIfIFollowThisUser(userId: String): Boolean {
        val result = firestore.collection(FOLLOWS_DB_PATH)
            .document(auth.getActiveUserId())
            .get()
            .await()
            .toObject(FollowsWrapper::class.java)

        return result?.follows?.any { it == userId } ?: false
    }
}
