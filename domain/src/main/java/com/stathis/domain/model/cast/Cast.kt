package com.stathis.domain.model.cast

import com.stathis.domain.model.UiModel

data class Cast(
    val cast_id: Int,
    val character: String,
    val credit_id: String,
    val id: Int,
    val name: String,
    val order: Int,
    val profile_path: String
) : UiModel {
    override fun equalsContent(obj: UiModel): Boolean = when (obj) {
        is Cast -> cast_id == obj.cast_id && name == obj.name
        else -> false
    }
}