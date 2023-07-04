package com.stathis.data.repository

import com.stathis.core.ext.getAndMapResponse
import com.stathis.core.util.session.SessionManager
import com.stathis.data.api.SeriesApi
import com.stathis.data.mappers.GuestSessionMapper
import com.stathis.domain.model.GuestSessionResponse
import com.stathis.domain.repositories.SessionRepository
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(
    private val api: SeriesApi,
    private val sessionManager: SessionManager
) : SessionRepository {

    override suspend fun createGuestSession(): GuestSessionResponse {
        val model = getAndMapResponse(
            call = { api.createGuesSession() },
            mapper = { GuestSessionMapper.toDomainModel(it) }
        )

        sessionManager.storeGuestSessionData(model)
        return model
    }
}