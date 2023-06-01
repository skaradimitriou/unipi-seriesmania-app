package com.stathis.domain.model.forum

import com.stathis.domain.model.UiModel
import com.stathis.domain.model.profile.User

data class ForumThread(
    val title: String,
    val body: String,
    val userId: String,
    val user: User
) : UiModel {
    override fun equalsContent(obj: UiModel): Boolean = when (obj) {
        is Thread -> true
        else -> false
    }
}
