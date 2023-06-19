package com.stathis.domain.usecases.follow

import com.stathis.domain.model.profile.OtherUser
import com.stathis.domain.repositories.CommunityRepository
import com.stathis.domain.usecases.BaseUseCase
import javax.inject.Inject

class FollowUserUseCase @Inject constructor(
    private val repo: CommunityRepository
) : BaseUseCase<Unit> {

    override suspend fun invoke(vararg args: Any?) {
        val user = args.getOrNull(0) as OtherUser
        return repo.follow(user)
    }
}