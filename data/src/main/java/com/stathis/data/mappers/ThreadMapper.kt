package com.stathis.data.mappers

import com.stathis.core.ext.toListOf
import com.stathis.data.model.ForumThreadDto
import com.stathis.domain.model.forum.ForumThread

object ThreadMapper : BaseMapper<List<ForumThreadDto>?, List<ForumThread>> {

    override fun toDomainModel(dto: List<ForumThreadDto>?): List<ForumThread> = dto.toListOf {
        SingleThreadMapper.toDomainModel(it)
    }
}