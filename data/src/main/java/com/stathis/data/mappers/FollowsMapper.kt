package com.stathis.data.mappers

import com.stathis.core.ext.toListOf
import com.stathis.core.ext.toNotNull
import com.stathis.domain.model.follows.FollowsWrapper
import com.stathis.domain.model.profile.OtherUser

object FollowsMapper : BaseMapper<FollowsWrapper?, List<OtherUser>> {

    override fun toDomainModel(dto: FollowsWrapper?): List<OtherUser> {
        return dto?.follows.toListOf {
            OtherUser(
                id = it.id.toNotNull(),
                username = it.username.toNotNull(),
                bio = it.bio.toNotNull(),
                email = it.email.toNotNull(),
                userImg = it.userImg.toNotNull(),
                preferences = it.preferences.toNotNull()
            )
        }
    }
}
