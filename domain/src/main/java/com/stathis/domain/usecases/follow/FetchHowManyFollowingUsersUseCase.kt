package com.stathis.domain.usecases.follow

import com.stathis.domain.repositories.CommunityRepository
import com.stathis.domain.usecases.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchHowManyFollowingUsersUseCase @Inject constructor(
    private val repo: CommunityRepository
) : BaseUseCase<Flow<Int>> {

    override suspend fun invoke(vararg args: Any?): Flow<Int> {
        val userId = args.getOrNull(0) as? String
        return repo.fetchMyFollowingUsersCount(userId)
    }
}