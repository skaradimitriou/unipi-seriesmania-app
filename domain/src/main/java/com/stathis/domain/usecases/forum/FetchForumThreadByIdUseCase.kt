package com.stathis.domain.usecases.forum

import com.stathis.domain.model.forum.Thread
import com.stathis.domain.repositories.ForumRepository
import com.stathis.domain.usecases.BaseUseCase
import javax.inject.Inject

class FetchForumThreadByIdUseCase @Inject constructor(
    private val repo: ForumRepository
) : BaseUseCase<Thread> {

    override suspend fun invoke(vararg args: Any?): Thread {
        val threadId = args.getOrNull(0) as? String ?: ""
        return repo.fetchThreadById(threadId)
    }
}