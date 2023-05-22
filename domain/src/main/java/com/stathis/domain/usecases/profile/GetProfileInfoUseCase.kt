package com.stathis.domain.usecases.profile

import com.stathis.domain.model.profile.User
import com.stathis.domain.repositories.ProfileRepository
import com.stathis.domain.usecases.BaseUseCase
import javax.inject.Inject

class GetProfileInfoUseCase @Inject constructor(
    private val repo: ProfileRepository
) : BaseUseCase<User> {

    override suspend fun invoke(vararg args: Any?): User = repo.getUserProfile()
}