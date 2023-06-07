package com.stathis.domain.usecases.forum

import com.stathis.domain.model.forum.Thread
import com.stathis.domain.repositories.ForumRepository
import com.stathis.domain.usecases.BaseUseCase
import javax.inject.Inject

class AddThreadReplyUseCase @Inject constructor(
    private val repo: ForumRepository
) : BaseUseCase<Boolean> {

    override suspend fun invoke(vararg args: Any?): Boolean {
        val reply = args.getOrNull(0) as? String ?: ""
        val thread = args.getOrNull(1) as Thread
        return repo.addNewReply(reply, thread)
    }
}