package com.stathis.domain.usecases.profile

import com.stathis.domain.model.profile.OtherUser
import com.stathis.domain.repositories.ProfileRepository
import com.stathis.domain.usecases.BaseUseCase
import javax.inject.Inject

class FetchUserByIdUseCase @Inject constructor(
    private val repo: ProfileRepository
) : BaseUseCase<OtherUser> {

    override suspend fun invoke(vararg args: Any?): OtherUser {
        val userId = args.getOrNull(0) as? String ?: ""
        return repo.getUserById(userId)
    }
}