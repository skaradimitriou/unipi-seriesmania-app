package com.stathis.domain.usecases.forum

import com.stathis.domain.model.forum.Thread
import com.stathis.domain.repositories.ForumRepository
import com.stathis.domain.usecases.BaseUseCase
import javax.inject.Inject

class FetchForumThreadsUseCase @Inject constructor(
    private val repo: ForumRepository
) : BaseUseCase<List<Thread>> {

    override suspend fun invoke(vararg args: Any?): List<Thread> = repo.fetchAllThreads()
}