package com.stathis.data.mappers

import com.stathis.core.ext.toNotNull
import com.stathis.data.model.UserDto
import com.stathis.domain.model.profile.User

object UserMapper : BaseMapper<UserDto?, User> {

    override suspend fun toDomainModel(dto: UserDto?): User = User(
        firstName = dto?.firstName.toNotNull(),
        lastName = dto?.lastName.toNotNull(),
        telephone = dto?.telephone.toNotNull(),
        email = dto?.email.toNotNull(),
        userImg = dto?.userImg.toNotNull(),
        username = dto?.username.toNotNull()
    )
}