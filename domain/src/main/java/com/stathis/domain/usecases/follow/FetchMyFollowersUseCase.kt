package com.stathis.domain.usecases.follow

import com.stathis.domain.model.profile.OtherUser
import com.stathis.domain.repositories.CommunityRepository
import com.stathis.domain.usecases.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchMyFollowersUseCase @Inject constructor(
    private val repo: CommunityRepository
) : BaseUseCase<Flow<List<OtherUser>>> {

    override suspend fun invoke(vararg args: Any?) = repo.getMyFollowers()
}