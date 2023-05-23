package com.stathis.seriesmania.ui.dashboard.discover

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.stathis.domain.model.genres.Genre
import com.stathis.domain.usecases.genres.GetGenresUseCase
import com.stathis.seriesmania.base.BaseViewModel
import com.stathis.seriesmania.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    app: Application,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val genreUseCase: GetGenresUseCase
) : BaseViewModel(app) {

    val genres: LiveData<List<Genre>>
        get() = _genres

    private val _genres = MutableLiveData<List<Genre>>()

    fun getAvailableGenres() {
        viewModelScope.launch(dispatcher) {
            val result = genreUseCase.invoke()
            _genres.postValue(result)
        }
    }
}