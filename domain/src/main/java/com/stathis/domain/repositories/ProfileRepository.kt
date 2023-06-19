package com.stathis.domain.repositories

import android.graphics.Bitmap
import com.stathis.domain.model.Result
import com.stathis.domain.model.profile.OtherUser
import com.stathis.domain.model.profile.User
import com.stathis.domain.model.profile.uimodel.SeriesPreference

interface ProfileRepository {

    suspend fun createNewUserProfile(email: String)

    suspend fun getUserProfile(): User

    suspend fun getUserInfo(userId: String): User

    suspend fun getUserById(userId: String): OtherUser

    suspend fun uploadProfileImage(userImage: Bitmap): Result<Boolean>

    suspend fun updateProfileInfo(username: String, bio: String): Boolean

    suspend fun saveUserPreferences(list : List<SeriesPreference>): Boolean

    suspend fun logout(): Boolean
}