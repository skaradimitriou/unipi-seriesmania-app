package com.stathis.domain.model.follows

import com.stathis.domain.model.UiModel

data class FollowsWrapper(
    val follows: List<String>
) : UiModel {
    constructor() : this(listOf())

    override fun equalsContent(obj: UiModel): Boolean = when (obj) {
        is FollowsWrapper -> follows == obj.follows
        else -> false
    }
}
