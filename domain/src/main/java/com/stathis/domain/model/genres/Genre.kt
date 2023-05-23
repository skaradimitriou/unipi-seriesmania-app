package com.stathis.domain.model.genres

import com.stathis.domain.model.UiModel

data class Genre(
    val id: Int,
    val name: String
) : UiModel {
    override fun equalsContent(obj: UiModel): Boolean = false
}
