package com.stathis.domain.usecases.forum

import com.stathis.domain.model.forum.ForumThread
import com.stathis.domain.repositories.ForumRepository
import com.stathis.domain.usecases.BaseUseCase
import javax.inject.Inject

class AddThreadReplyUseCase @Inject constructor(
    private val repo: ForumRepository
) : BaseUseCase<Boolean> {

    override suspend fun invoke(vararg args: Any?): Boolean {
        val reply = args.getOrNull(0) as? String ?: ""
        val forumThread = args.getOrNull(1) as ForumThread
        return repo.addNewReply(reply, forumThread)
    }
}