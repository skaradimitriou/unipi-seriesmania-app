package com.stathis.domain.usecases.forum

import com.stathis.domain.model.forum.ForumThread
import com.stathis.domain.repositories.ForumRepository
import com.stathis.domain.usecases.BaseUseCase
import javax.inject.Inject

class FetchForumThreadsUseCase @Inject constructor(
    private val repo: ForumRepository
) : BaseUseCase<List<ForumThread>> {

    override suspend fun invoke(vararg args: Any?): List<ForumThread> = repo.fetchAllThreads()
}