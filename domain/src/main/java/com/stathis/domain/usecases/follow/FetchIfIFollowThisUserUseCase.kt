package com.stathis.domain.usecases.follow

import com.stathis.domain.repositories.CommunityRepository
import com.stathis.domain.usecases.BaseUseCase
import javax.inject.Inject

class FetchIfIFollowThisUserUseCase @Inject constructor(
    private val repo: CommunityRepository
) : BaseUseCase<Boolean> {

    override suspend fun invoke(vararg args: Any?): Boolean {
        val userId = args.getOrNull(0) as? String ?: ""
        return repo.fetchIfIFollowThisUser(userId)
    }
}