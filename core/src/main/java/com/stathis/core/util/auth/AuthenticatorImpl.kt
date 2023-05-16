package com.stathis.core.util.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.stathis.core.util.consts.GENERIC_ERROR
import com.stathis.domain.model.Result
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthenticatorImpl @Inject constructor(
    private val auth: FirebaseAuth
) : Authenticator {

    /**
     * Attempts to register a user (email, pass) async and returns the result.
     */

    override suspend fun register(email: String, pass: String): Result<Boolean> {
        try {
            val task = auth.createUserWithEmailAndPassword(email, pass).await()
            task.user?.let {
                return Result.Success(true)
            } ?: kotlin.run {
                return Result.Failure(GENERIC_ERROR)
            }
        } catch (e: FirebaseAuthException) {
            return Result.Failure(e.localizedMessage ?: GENERIC_ERROR)
        }
    }

    /**
     * Attempts to login a user (email, pass) async and returns the result.
     */

    override suspend fun login(email: String, pass: String): Result<FirebaseUser> {
        try {
            val task = auth.signInWithEmailAndPassword(email, pass).await()
            task.user?.let {
                return Result.Success(it)
            } ?: run {
                return Result.Failure(GENERIC_ERROR)
            }
        } catch (e: FirebaseAuthException) {
            return Result.Failure(e.localizedMessage ?: GENERIC_ERROR)
        }
    }

    override fun isUserActive(): Boolean = auth.currentUser != null

    override fun getActiveUser(): FirebaseUser? = auth.currentUser

    override fun getActiveUserId(): String = auth.currentUser?.uid.toString()

    override fun logout(): Boolean {
        auth.signOut()
        return auth.currentUser == null
    }
}