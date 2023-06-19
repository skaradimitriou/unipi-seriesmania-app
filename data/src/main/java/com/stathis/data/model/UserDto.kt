package com.stathis.data.model

import com.stathis.domain.model.profile.uimodel.SeriesPreference

data class UserDto(
    val id: String? = null,
    val username: String? = null,
    val bio: String? = null,
    val preferences: List<SeriesPreference>? = null,
    val email: String? = null,
    val userImg: String? = null
)
