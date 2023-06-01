package com.stathis.domain.usecases.profile

import com.stathis.domain.model.profile.User
import com.stathis.domain.repositories.ProfileRepository
import com.stathis.domain.usecases.BaseUseCase
import javax.inject.Inject

class FetchUserInfoUseCase @Inject constructor(
    private val repo: ProfileRepository
) : BaseUseCase<User> {

    override suspend fun invoke(vararg args: Any?): User {
        val userId = args.firstOrNull() as? String ?: ""
        return repo.getUserInfo(userId)
    }
}