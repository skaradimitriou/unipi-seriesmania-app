package com.stathis.data.mappers

import com.stathis.core.ext.toNotNull
import com.stathis.data.model.UserInfoDto
import com.stathis.domain.model.profile.UserInfo

object UserInfoMapper : BaseMapper<UserInfoDto?, UserInfo> {

    override fun toDomainModel(dto: UserInfoDto?): UserInfo = UserInfo(
        username = dto?.username.toNotNull(),
        userImg = dto?.userImg.toNotNull()
    )
}