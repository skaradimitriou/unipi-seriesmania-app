package com.stathis.domain.repositories

interface ProfileRepository {

    suspend fun createNewUserProfile(email: String)
}