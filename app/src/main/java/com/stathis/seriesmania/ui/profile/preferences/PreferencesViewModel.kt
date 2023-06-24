package com.stathis.seriesmania.ui.profile.preferences

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.stathis.core.base.BaseViewModel
import com.stathis.domain.model.UiModel
import com.stathis.domain.model.profile.uimodel.SeriesPreference
import com.stathis.domain.usecases.profile.GetProfileInfoUseCase
import com.stathis.domain.usecases.profile.SaveSeriesPreferenceUseCase
import com.stathis.seriesmania.di.IoDispatcher
import com.stathis.seriesmania.ui.profile.preferences.generator.PreferencesGenerator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreferencesViewModel @Inject constructor(
    app: Application,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val getProfileInfoUseCase: GetProfileInfoUseCase,
    private val savePreferencesUseCase: SaveSeriesPreferenceUseCase
) : BaseViewModel(app) {

    val preferencesSaved: LiveData<Boolean>
        get() = _preferencesSaved

    private val _preferencesSaved = MutableLiveData<Boolean>()

    val preferences: LiveData<List<SeriesPreference>>
        get() = _preferences

    private val _preferences = MutableLiveData<List<SeriesPreference>>()

    val buttonState: LiveData<Boolean>
        get() = _buttonState

    private val _buttonState = MutableLiveData<Boolean>()

    private var selectedPreferences: List<UiModel>? = null

    fun getPreferences() {
        viewModelScope.launch(dispatcher) {
            val userPrefs = getProfileInfoUseCase.invoke().preferences
            val data = PreferencesGenerator.generatePreferences().map { pref ->
                pref.apply {
                    selected = userPrefs.any { it.id == pref.id }
                }
            }
            validateSelection(data)
            _preferences.postValue(data)
        }
    }

    fun validateSelection(selection: List<UiModel>) {
        val selectedItems = selection.filter { (it as SeriesPreference).selected }
        selectedPreferences = selectedItems
        val isValid =
            selection.isNotEmpty() && selectedItems.isNotEmpty() && selectedItems.size <= 3
        _buttonState.postValue(isValid)
    }

    fun saveSelection() {
        viewModelScope.launch(dispatcher) {
            val result = savePreferencesUseCase.invoke(selectedPreferences)
            _preferencesSaved.postValue(result)
        }
    }
}