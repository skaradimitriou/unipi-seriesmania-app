package com.stathis.domain.repositories

import com.stathis.domain.model.profile.User
import kotlinx.coroutines.flow.Flow

interface CommunityRepository {

    suspend fun follow(userId: String)

    suspend fun unfollow(userId: String)

    suspend fun getMyFollowers(userId: String? = null): Flow<List<String>>

    suspend fun getMyFollowersDetails(userId: String? = null): Flow<List<User>>

    suspend fun getWhoFollowsMe(userId: String? = null): Flow<List<User>>

    suspend fun fetchMyFollowingUsersCount(userId: String? = null): Flow<Int>

    suspend fun fetchMyFollowersCount(userId: String? = null): Flow<Int>

    suspend fun fetchIfIFollowThisUser(userId: String): Boolean
}