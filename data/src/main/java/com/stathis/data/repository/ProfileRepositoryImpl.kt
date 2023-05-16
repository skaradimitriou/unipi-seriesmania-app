package com.stathis.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.stathis.core.util.auth.Authenticator
import com.stathis.data.util.USERS_DB_PATH
import com.stathis.domain.repositories.ProfileRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: Authenticator
) : ProfileRepository {

    override suspend fun createNewUserProfile(email: String) {
        val data: HashMap<String, String> = hashMapOf(
            "username" to email.takeWhile { it != '@' },
            "firstName" to "",
            "lastName" to "",
            "userImg" to "",
            "email" to email,
            "telephone" to ""
        )

        val uuid = auth.getActiveUserId()
        firestore.collection(USERS_DB_PATH).document(uuid).set(data).await()
    }
}