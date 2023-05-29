package com.stathis.domain.model.genres

import android.os.Parcelable
import com.stathis.domain.model.UiModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Genre(
    val id: Int, val name: String
) : UiModel, Parcelable {
    override fun equalsContent(obj: UiModel): Boolean = when (obj) {
        is Genre -> id == obj.id && name == obj.name
        else -> false
    }
}
