package com.stathis.data.model

data class RatingResponseDto(
    val success: Boolean,
    val status_code: Int,
    val status_message: String
)