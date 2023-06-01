package com.stathis.domain.model.forum

import com.stathis.domain.model.UiModel

data class Thread(
    val title: String,
    val body: String,
    val userId: String
) : UiModel {
    override fun equalsContent(obj: UiModel): Boolean = when (obj) {
        is Thread -> true
        else -> false
    }
}
