package com.stathis.domain.repositories

import com.stathis.domain.model.profile.OtherUser
import kotlinx.coroutines.flow.Flow

interface CommunityRepository {

    suspend fun follow(user: OtherUser)

    suspend fun unfollow(user: OtherUser)

    suspend fun getMyFollowers(): Flow<List<OtherUser>>

    suspend fun getWhoFollowsMe(): Flow<List<OtherUser>>

    suspend fun isUserFollowedByMe(userId: String): Boolean
}