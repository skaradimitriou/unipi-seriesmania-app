package com.stathis.domain.usecases.forum

import com.stathis.domain.repositories.ForumRepository
import com.stathis.domain.usecases.BaseUseCase
import javax.inject.Inject

class AddNewThreadUseCase @Inject constructor(
    private val repo: ForumRepository
) : BaseUseCase<Boolean> {

    override suspend fun invoke(vararg args: Any?): Boolean {
        val title = args.getOrNull(0) as? String ?: ""
        val body = args.getOrNull(1) as? String ?: ""
        return repo.addNewThread(title, body)
    }
}