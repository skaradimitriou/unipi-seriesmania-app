package com.stathis.data.repository

import com.stathis.core.util.auth.Authenticator
import com.stathis.domain.model.Result
import com.stathis.domain.repositories.OnboardingRepository
import javax.inject.Inject

class OnboardingRepositoryImpl @Inject constructor(
    private val authenticator: Authenticator
) : OnboardingRepository {

    override suspend fun registerNewUser(email: String, password: String): Result<Boolean> {
        return authenticator.register(email, password)
    }
}