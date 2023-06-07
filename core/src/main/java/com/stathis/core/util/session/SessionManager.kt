package com.stathis.core.util.session

import com.stathis.domain.model.profile.User
import kotlinx.coroutines.flow.Flow

/**
 * [SessionManager] holds the information about who the active
 * user is inside the application.
 */

interface SessionManager {

    /**
     * Method to store the user data in dataStore (K,V)
     */

    suspend fun storeUserData(data: User)

    /**
     * Method to retrieve the user's data from datastore
     */

    suspend fun retrieveUserData(): Flow<User>

    /**
     * Clears the user data stored
     */

    suspend fun clearUserData()
}