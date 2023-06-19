package com.stathis.domain.usecases.profile

import com.stathis.domain.repositories.ProfileRepository
import com.stathis.domain.usecases.BaseUseCase
import javax.inject.Inject

class UpdateProfileInfoUseCase @Inject constructor(
    private val repo: ProfileRepository
) : BaseUseCase<Boolean> {

    override suspend fun invoke(vararg args: Any?): Boolean {
        val username = args.getOrNull(0) as? String ?: ""
        val bio = args.getOrNull(1) as? String ?: ""
        return repo.updateProfileInfo(username, bio)
    }
}