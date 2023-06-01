package com.stathis.data.mappers

import com.stathis.core.ext.toListOf
import com.stathis.core.ext.toNotNull
import com.stathis.data.model.ThreadDto

object ThreadMapper : BaseMapper<List<ThreadDto>?, List<com.stathis.domain.model.forum.Thread>> {

    override suspend fun toDomainModel(dto: List<ThreadDto>?): List<com.stathis.domain.model.forum.Thread> {
        return dto.toListOf {
            com.stathis.domain.model.forum.Thread(
                title = it.title.toNotNull(),
                body = it.body.toNotNull(),
                userId = it.userId.toNotNull()
            )
        }
    }
}