package com.stathis.seriesmania.ui.results

import android.app.Application
import com.stathis.domain.model.cast.Cast
import com.stathis.seriesmania.base.BaseViewModel

class ResultsSharedViewModel(
    app: Application
) : BaseViewModel(app) {

    private var _person: Cast? = null

    fun setPersonId(cast: Cast) {
        _person = cast
    }

    fun getPerson() = _person

}