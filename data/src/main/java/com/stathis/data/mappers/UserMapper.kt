package com.stathis.data.mappers

import com.stathis.core.ext.toNotNull
import com.stathis.data.model.UserDto
import com.stathis.domain.model.profile.User

object UserMapper : BaseMapper<UserDto?, User> {

    override fun toDomainModel(dto: UserDto?): User = User(
        id = dto?.id.toNotNull(),
        username = dto?.username.toNotNull(),
        bio = dto?.bio.toNotNull(),
        email = dto?.email.toNotNull(),
        userImg = dto?.userImg.toNotNull(),
        preferences = dto?.preferences.toNotNull()
    )
}