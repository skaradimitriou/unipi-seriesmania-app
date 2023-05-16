package com.stathis.domain.usecases.profile

import com.stathis.domain.repositories.ProfileRepository
import com.stathis.domain.usecases.BaseUseCase
import javax.inject.Inject

class CreateNewUserProfileUseCase @Inject constructor(
    private val repo: ProfileRepository
) : BaseUseCase<Unit> {

    override suspend fun invoke(vararg args: Any?) {
        val email = args.getOrNull(0) as? String ?: ""
        return repo.createNewUserProfile(email)
    }
}