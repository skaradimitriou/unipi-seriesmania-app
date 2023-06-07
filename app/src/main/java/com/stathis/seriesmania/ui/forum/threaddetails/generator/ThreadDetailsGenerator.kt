package com.stathis.seriesmania.ui.forum.threaddetails.generator

import com.stathis.domain.model.UiModel

object ThreadDetailsGenerator {

    fun generate(thread: com.stathis.domain.model.forum.Thread): List<UiModel> {
        val data = mutableListOf<UiModel>()
        data.add(thread)
        data.addAll(thread.replies)
        return data
    }
}