package com.stathis.seriesmania.ui.results

import android.app.Application
import com.stathis.core.base.BaseViewModel
import com.stathis.domain.model.cast.Cast

class ResultsSharedViewModel(
    app: Application
) : BaseViewModel(app) {

    private var _person: Cast? = null

    fun setPersonId(cast: Cast) {
        _person = cast
    }

    fun getPerson() = _person
}