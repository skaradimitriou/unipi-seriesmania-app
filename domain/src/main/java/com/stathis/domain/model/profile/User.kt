package com.stathis.domain.model.profile

import com.stathis.domain.model.UiModel

data class User(
    val firstName: String,
    val lastName: String,
    val telephone: String,
    val email: String,
    val userImg: String,
    val username: String,
) : UiModel {
    override fun equalsContent(obj: UiModel): Boolean = false
}