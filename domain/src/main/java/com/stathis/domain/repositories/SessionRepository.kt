package com.stathis.domain.repositories

import com.stathis.domain.model.GuestSessionResponse

interface SessionRepository {

    suspend fun createGuestSession(): GuestSessionResponse
}