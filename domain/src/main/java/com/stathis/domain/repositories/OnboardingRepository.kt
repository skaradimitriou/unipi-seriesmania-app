package com.stathis.domain.repositories

import com.stathis.domain.model.Result

interface OnboardingRepository {

    suspend fun registerNewUser(
        email: String,
        password: String
    ): Result<Boolean>

    suspend fun performLogin(
        email: String,
        password: String
    ): Result<Boolean>
}