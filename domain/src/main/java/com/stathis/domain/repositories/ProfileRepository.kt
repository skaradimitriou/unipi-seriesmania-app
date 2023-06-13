package com.stathis.domain.repositories

import android.graphics.Bitmap
import com.stathis.domain.model.profile.OtherUser
import com.stathis.domain.model.profile.User

interface ProfileRepository {

    suspend fun createNewUserProfile(email: String)

    suspend fun getUserProfile(): User

    suspend fun getUserInfo(userId: String): User

    suspend fun getUserById(userId: String): OtherUser

    suspend fun uploadProfileImage(userImage: Bitmap): Boolean

    suspend fun logout(): Boolean
}