package com.stathis.domain.combiners

import com.stathis.domain.model.forum.ForumThread
import com.stathis.domain.usecases.forum.FetchForumThreadsUseCase
import com.stathis.domain.usecases.profile.FetchUserInfoUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class ForumThreadsCombiner @Inject constructor(
    private val fetchForumThreadsUseCase: FetchForumThreadsUseCase,
    private val fetchUserInfoUseCase: FetchUserInfoUseCase
) : BaseCombiner<List<ForumThread>> {

    override suspend fun invoke(vararg args: Any?): List<ForumThread> = coroutineScope {
        val forumThreads = async { fetchForumThreadsUseCase.invoke() }.await()

        val result = forumThreads.map {
            ForumThread(
                title = it.title,
                body = it.body,
                userId = it.userId,
                user = async { fetchUserInfoUseCase.invoke(it.userId) }.await()
            )
        }

        return@coroutineScope result
    }
}