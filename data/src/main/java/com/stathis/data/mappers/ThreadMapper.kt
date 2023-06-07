package com.stathis.data.mappers

import com.stathis.core.ext.toListOf
import com.stathis.data.model.ThreadDto
import com.stathis.domain.model.forum.Thread

object ThreadMapper : BaseMapper<List<ThreadDto>?, List<Thread>> {

    override fun toDomainModel(dto: List<ThreadDto>?): List<Thread> = dto.toListOf {
        SingleThreadMapper.toDomainModel(it)
    }
}