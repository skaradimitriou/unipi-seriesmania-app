package com.stathis.domain.model


data class GuestSessionResponse(
    val success: Boolean,
    val guestSessionId: String,
    val expiresAt: String
) {
    fun sessionExpired(): Boolean {
        return false
//        val expireDate = ZonedDateTime.parse(expiresAt)
//        val currentDate = ZonedDateTime.now(ZoneOffset.UTC)
//        return expireDate > currentDate
    }
}
