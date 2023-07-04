package com.stathis.seriesmania.ui.profile.preferences.generator

import com.stathis.core.util.GenresGenerator
import com.stathis.domain.model.profile.uimodel.SeriesPreference

object PreferencesGenerator {

    fun generatePreferences() = GenresGenerator.genres.map {
        SeriesPreference(id = it.id, name = it.name)
    }
}