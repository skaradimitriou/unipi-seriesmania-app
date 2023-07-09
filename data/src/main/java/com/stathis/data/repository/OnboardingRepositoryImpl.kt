package com.stathis.data.repository

import com.stathis.core.util.auth.Authenticator
import com.stathis.domain.model.Result
import com.stathis.domain.repositories.OnboardingRepository
import com.stathis.domain.repositories.SessionRepository
import javax.inject.Inject

class OnboardingRepositoryImpl @Inject constructor(
    private val authenticator: Authenticator,
    private val sessionRepository: SessionRepository
) : OnboardingRepository {

    override suspend fun registerNewUser(email: String, password: String): Result<Boolean> {
        return authenticator.register(email, password)
    }

    override suspend fun performLogin(email: String, password: String): Result<Boolean> {
        val loginResult = authenticator.login(email, password)
        sessionRepository.createGuestSession()
        return loginResult
    }

    override suspend fun checkIfUserActive() = authenticator.getActiveUser() != null
}