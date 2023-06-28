package com.stathis.data.mappers

import com.stathis.core.ext.toNotNull
import com.stathis.data.model.GuestSessionResponseDto
import com.stathis.domain.model.GuestSessionResponse

object GuestSessionMapper : BaseMapper<GuestSessionResponseDto?, GuestSessionResponse> {

    override fun toDomainModel(dto: GuestSessionResponseDto?) = GuestSessionResponse(
        success = dto?.success.toNotNull(),
        guestSessionId = dto?.guest_session_id.toNotNull(),
        expiresAt = dto?.expires_at.toNotNull()
    )
}