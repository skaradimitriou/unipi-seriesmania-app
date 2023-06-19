package com.stathis.domain.usecases.forum

import com.stathis.domain.model.forum.ForumThread
import com.stathis.domain.repositories.ForumRepository
import com.stathis.domain.usecases.BaseUseCase
import javax.inject.Inject

class FetchForumThreadByIdUseCase @Inject constructor(
    private val repo: ForumRepository
) : BaseUseCase<ForumThread> {

    override suspend fun invoke(vararg args: Any?): ForumThread {
        val threadId = args.getOrNull(0) as? String ?: ""
        return repo.fetchThreadById(threadId)
    }
}