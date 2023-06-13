package com.stathis.data.mappers

import com.stathis.core.ext.toNotNull
import com.stathis.data.model.UserDto
import com.stathis.domain.model.profile.OtherUser

object OtherUserMapper : BaseMapper<UserDto?, OtherUser> {

    override fun toDomainModel(dto: UserDto?): OtherUser = OtherUser(
        id = dto?.id.toNotNull(),
        username = dto?.username.toNotNull(),
        bio = dto?.bio.toNotNull(),
        email = dto?.email.toNotNull(),
        userImg = dto?.userImg.toNotNull(),
        preferences = dto?.preferences.toNotNull()
    )
}