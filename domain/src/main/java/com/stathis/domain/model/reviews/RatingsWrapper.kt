package com.stathis.domain.model.reviews

import com.stathis.domain.model.UiModel

data class RatingsWrapper(
    val ratings: List<Rating>
) : UiModel {
    constructor() : this(listOf())

    override fun equalsContent(obj: UiModel): Boolean = when (obj) {
        is RatingsWrapper -> ratings == obj.ratings
        else -> false
    }
}