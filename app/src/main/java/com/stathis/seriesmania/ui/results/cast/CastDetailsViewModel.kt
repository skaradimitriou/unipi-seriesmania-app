package com.stathis.seriesmania.ui.results.cast

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.stathis.domain.combiners.ActorDetailsCombiner
import com.stathis.domain.model.UiModel
import com.stathis.seriesmania.base.BaseViewModel
import com.stathis.seriesmania.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CastDetailsViewModel @Inject constructor(
    app: Application,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val detailsCombiner: ActorDetailsCombiner
) : BaseViewModel(app) {

    val details: MutableLiveData<List<UiModel>>
        get() = _details

    private val _details = MutableLiveData<List<UiModel>>()

    fun getData(personId: Int) {
        viewModelScope.launch(dispatcher) {
            val result = detailsCombiner.invoke(personId)
            _details.postValue(result)
        }
    }
}