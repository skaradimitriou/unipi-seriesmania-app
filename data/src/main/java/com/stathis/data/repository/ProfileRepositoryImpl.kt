package com.stathis.data.repository

import android.graphics.Bitmap
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.stathis.core.ext.compressBitmap
import com.stathis.core.util.auth.Authenticator
import com.stathis.core.util.session.SessionManager
import com.stathis.data.mappers.UserMapper
import com.stathis.data.model.UserDto
import com.stathis.data.util.USERS_DB_PATH
import com.stathis.domain.model.Result
import com.stathis.domain.model.profile.User
import com.stathis.domain.model.profile.uimodel.SeriesPreference
import com.stathis.domain.repositories.ProfileRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: Authenticator,
    private val sessionManager: SessionManager,
    private val storage: StorageReference
) : ProfileRepository {

    override suspend fun createNewUserProfile(email: String) {
        val uuid = auth.getActiveUserId()
        val data: HashMap<String, String> = hashMapOf(
            "id" to uuid, "username" to email.takeWhile { it != '@' }, "bio" to "", "email" to email
        )

        firestore.collection(USERS_DB_PATH).document(uuid).set(data).await()
    }

    override suspend fun getUserProfile(): User {
        val result = firestore.collection(USERS_DB_PATH)
            .document(auth.getActiveUserId())
            .get()
            .await()
            .toObject(UserDto::class.java)

        val mappedResult = UserMapper.toDomainModel(result)

        //saves the user data to the session manager
        sessionManager.storeUserData(mappedResult)

        return mappedResult
    }

    override suspend fun getUserInfo(userId: String): User {
        val result = firestore
            .collection(USERS_DB_PATH)
            .document(userId)
            .get()
            .await()
            .toObject(UserDto::class.java)

        return UserMapper.toDomainModel(result)
    }

    override suspend fun uploadProfileImage(userImage: Bitmap): Result<Boolean> {
        val storageRef = storage.child("profile/${auth.getActiveUserId()}")
        val image = userImage.compressBitmap()
        val upload = storageRef.putBytes(image).await()
        val downloadUrl = upload.metadata?.reference?.downloadUrl?.await().toString()

        val data: HashMap<String, Any> = hashMapOf(
            "userImg" to downloadUrl
        )

        firestore.collection(USERS_DB_PATH).document(auth.getActiveUserId()).update(data).await()
        return Result.Success(true)
    }

    override suspend fun updateProfileInfo(username: String, bio: String): Boolean {
        val uuid = auth.getActiveUserId()
        val data = mapOf(
            "username" to username,
            "bio" to bio,
        )

        firestore.collection(USERS_DB_PATH).document(uuid).update(data).await()
        return true
    }

    override suspend fun saveUserPreferences(list: List<SeriesPreference>): Boolean {
        val uuid = auth.getActiveUserId()
        val data = mapOf("preferences" to list)
        firestore.collection(USERS_DB_PATH).document(uuid).update(data).await()
        return true
    }

    override suspend fun logout(): Boolean = auth.logout()
}