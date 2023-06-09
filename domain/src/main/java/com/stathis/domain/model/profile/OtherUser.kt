package com.stathis.domain.model.profile

import com.stathis.domain.model.UiModel

data class OtherUser(
    val id: String,
    val username: String,
    val bio: String,
    val email: String,
    val userImg: String
) : UiModel {
    override fun equalsContent(obj: UiModel): Boolean = when (obj) {
        is User -> userImg == obj.userImg && username == obj.username
        else -> false
    }
}
