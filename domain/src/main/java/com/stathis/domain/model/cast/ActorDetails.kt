package com.stathis.domain.model.cast

import com.stathis.domain.model.UiModel

data class ActorDetails(
    val adult: Boolean,
    val also_known_as: List<String>,
    val biography: String,
    val birthday: String,
    val gender: Int,
    val id: Int,
    val name: String,
    val place_of_birth: String,
    val popularity: Double,
    val profile_path: String,
    val known_for_department: String
) : UiModel {
    override fun equalsContent(obj: UiModel): Boolean = when (obj) {
        is ActorDetails -> id == obj.id && name == obj.name
        else -> false
    }
}