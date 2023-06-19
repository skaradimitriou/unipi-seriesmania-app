package com.stathis.seriesmania.ui.forum.threaddetails.generator

import com.stathis.domain.model.UiModel

object ThreadDetailsGenerator {

    fun generate(forumThread: com.stathis.domain.model.forum.ForumThread): List<UiModel> {
        val data = mutableListOf<UiModel>()
        data.add(forumThread)
        data.addAll(forumThread.replies)
        return data
    }
}