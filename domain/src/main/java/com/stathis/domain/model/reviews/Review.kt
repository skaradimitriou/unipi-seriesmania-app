package com.stathis.domain.model.reviews

import com.stathis.domain.model.UiModel

class Review(
    val id: String = "",
    var author: String = "",
    var content: String = "",
    val url: String = ""
) : UiModel {

    constructor(author: String, content: String) : this() {
        this.author = author
        this.content = content
    }

    override fun equalsContent(obj: UiModel): Boolean = when (obj) {
        is Review -> id == obj.id && author == obj.author && content == obj.content && url == obj.url
        else -> false
    }
}