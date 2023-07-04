package com.stathis.data.model

data class FaqDto(
    val title: String? = null,
    val description: String? = null,
    val seq: Int? = null
) {
    constructor() : this("", "", 0)
}
