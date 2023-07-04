package com.stathis.domain.model.faq

import com.stathis.domain.model.UiModel

data class Faq(
    val title: String,
    val description: String,
    val seq: Int,
    var isExpanded: Boolean = false
) : UiModel {
    constructor() : this("", "", 0, false)

    override fun equalsContent(obj: UiModel): Boolean = when (obj) {
        is Faq -> title == obj.title && description == obj.description
        else -> false
    }
}