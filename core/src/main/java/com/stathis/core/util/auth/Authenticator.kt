package com.stathis.core.util.auth

import com.google.firebase.auth.FirebaseUser
import com.stathis.domain.model.Result

/**
 * [AuthManager] is responsible for the authorization features inside the app.
 */

interface Authenticator {

    /**
     * Register feature
     */

    suspend fun register(email: String, pass: String): Result<Boolean>

    /**
     * Login Feature
     */

    suspend fun login(email: String, pass: String): Result<FirebaseUser>

    /**
     * Returns whether or not there is a user active at the moment.
     */

    fun isUserActive(): Boolean

    /**
     * Returns the current user that is logged in.
     */

    fun getActiveUser(): FirebaseUser?

    /**
     * Returns the current user's uuid.
     */

    fun getActiveUserId(): String

    /**
     * Logout feature
     */

    fun logout(): Boolean
}