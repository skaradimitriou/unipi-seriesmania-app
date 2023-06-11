package com.stathis.domain.model.reviews

import com.stathis.domain.model.UiModel

data class Review(
    val id: String,
    val author: String,
    val content: String,
    val url: String
) : UiModel {
    override fun equalsContent(obj: UiModel): Boolean = when (obj) {
        is Review -> id == obj.id && author == obj.author && content == obj.content && url == obj.url
        else -> false
    }
}