package com.stathis.domain.usecases.profile

import com.stathis.domain.repositories.ProfileRepository
import com.stathis.domain.usecases.BaseUseCase
import javax.inject.Inject

class FetchShortUserInfoUseCase @Inject constructor(
    private val repo: ProfileRepository
) : BaseUseCase<Boolean> {

    override suspend fun invoke(vararg args: Any?): Boolean {
        return false
    }
}