package com.stathis.domain.model.profile.uimodel

import com.stathis.domain.model.UiModel

data class LogoutOption(
    val buttonTxt: String
) : UiModel {
    override fun equalsContent(obj: UiModel): Boolean = true
}