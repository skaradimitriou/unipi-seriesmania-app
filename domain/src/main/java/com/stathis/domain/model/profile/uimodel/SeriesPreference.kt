package com.stathis.domain.model.profile.uimodel

import android.os.Parcelable
import com.stathis.domain.model.UiModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class SeriesPreference(
    val id: Int,
    val name: String,
    var selected: Boolean = false
) : UiModel, Parcelable {

    constructor() : this(0, "", false)

    override fun equalsContent(obj: UiModel): Boolean = when (obj) {
        is SeriesPreference -> id == obj.id && name == obj.name && selected == obj.selected
        else -> false
    }
}
