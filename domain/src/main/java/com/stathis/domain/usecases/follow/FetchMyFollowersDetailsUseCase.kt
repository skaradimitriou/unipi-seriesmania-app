package com.stathis.domain.usecases.follow

import com.stathis.domain.model.profile.User
import com.stathis.domain.repositories.CommunityRepository
import com.stathis.domain.usecases.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchMyFollowersDetailsUseCase @Inject constructor(
    private val repo: CommunityRepository
) : BaseUseCase<Flow<List<User>>> {

    override suspend fun invoke(vararg args: Any?): Flow<List<User>> {
        val userId = args.getOrNull(0) as? String
        return repo.getMyFollowersDetails(userId)
    }
}