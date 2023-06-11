package com.stathis.domain.repositories

import com.stathis.domain.model.profile.OtherUser
import com.stathis.domain.model.profile.User

interface ProfileRepository {

    suspend fun createNewUserProfile(email: String)

    suspend fun getUserProfile(): User

    suspend fun getUserInfo(userId: String): User

    suspend fun getUserById(userId: String): OtherUser

    suspend fun logout(): Boolean
}