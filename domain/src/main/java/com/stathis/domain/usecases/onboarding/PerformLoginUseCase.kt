package com.stathis.domain.usecases.onboarding

import com.stathis.domain.model.Result
import com.stathis.domain.repositories.OnboardingRepository
import com.stathis.domain.usecases.BaseUseCase
import javax.inject.Inject

class PerformLoginUseCase @Inject constructor(
    private val repo: OnboardingRepository
) : BaseUseCase<Result<Boolean>> {

    override suspend fun invoke(vararg args: Any?): Result<Boolean> {
        val email = args.getOrNull(0) as? String ?: ""
        val password = args.getOrNull(1) as? String ?: ""
        return repo.performLogin(email, password)
    }
}