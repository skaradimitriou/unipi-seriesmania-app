package com.stathis.core.util.session

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.stathis.core.util.USER_PROFILE
import com.stathis.domain.model.profile.User
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SessionManagerImpl @Inject constructor(
    @ApplicationContext context: Context,
    private val gson: Gson
) : SessionManager {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "USERS")
    private val datastore = context.dataStore
    private val userKey = stringPreferencesKey(USER_PROFILE)

    override suspend fun storeUserData(data: User) {
        datastore.edit { pref ->
            pref[userKey] = gson.toJson(data)
        }
    }

    override suspend fun retrieveUserData(): Flow<User> = datastore.data.map { prefs ->
        gson.fromJson(prefs[userKey] ?: "", User::class.java)
    }

    override suspend fun clearUserData() {
        datastore.edit {
            it.clear()
        }
    }
}