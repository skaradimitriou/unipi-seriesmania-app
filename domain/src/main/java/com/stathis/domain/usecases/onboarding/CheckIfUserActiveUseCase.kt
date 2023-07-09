package com.stathis.domain.usecases.onboarding

import com.stathis.domain.repositories.OnboardingRepository
import com.stathis.domain.usecases.BaseUseCase
import javax.inject.Inject

class CheckIfUserActiveUseCase @Inject constructor(
    private val repo: OnboardingRepository
) : BaseUseCase<Boolean> {

    override suspend fun invoke(vararg args: Any?) = repo.checkIfUserActive()
}