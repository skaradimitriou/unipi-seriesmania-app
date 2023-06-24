package com.stathis.data.model

data class ActorDetailsDto(
    val adult: Boolean? = null,
    val also_known_as: List<String>? = null,
    val biography: String? = null,
    val birthday: String? = null,
    val gender: Int? = null,
    val id: Int? = null,
    val name: String? = null,
    val place_of_birth: String? = null,
    val popularity: Double? = null,
    val profile_path: String? = null,
    val known_for_department: String
)