package com.stathis.seriesmania.ui.settings.intro

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stathis.core.adapters.settings.intro.SettingsIntroItem
import com.stathis.core.adapters.settings.intro.SettingsIntroType
import com.stathis.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsIntroViewModel @Inject constructor(
    app: Application
) : BaseViewModel(app) {

    val options: LiveData<List<SettingsIntroItem>>
        get() = _options

    private val _options = MutableLiveData<List<SettingsIntroItem>>()

    fun getSettingsItems() {
        val list = listOf(
            SettingsIntroItem(
                title = getString(com.stathis.core.R.string.analytics_title),
                type = SettingsIntroType.ANALYTICS
            ),
            SettingsIntroItem(
                title = getString(com.stathis.core.R.string.faq_long_title),
                type = SettingsIntroType.FAQ
            ),
            SettingsIntroItem(
                title = getString(com.stathis.core.R.string.about_app_title),
                type = SettingsIntroType.ABOUT
            )
        )
        _options.postValue(list)
    }
}