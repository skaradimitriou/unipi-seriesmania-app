package com.stathis.seriesmania.ui.results.details

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.stathis.core.base.BaseViewModel
import com.stathis.domain.combiners.SeriesDetailsCombiner
import com.stathis.domain.model.TvSeries
import com.stathis.domain.model.UiModel
import com.stathis.seriesmania.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    app: Application,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val combiner: SeriesDetailsCombiner
) : BaseViewModel(app) {

    val details: LiveData<List<UiModel>>
        get() = _details

    private val _details = MutableLiveData<List<UiModel>>()

    fun getSeriesInfo(series: TvSeries) {
        viewModelScope.launch(dispatcher) {
            val list = mutableListOf<UiModel>()
            list.add(series)

            val result = combiner.invoke(series.id)

            if (result.cast.results.isNotEmpty()) {
                list.add(result.cast)
            }

            if (result.similar.results.isNotEmpty()) {
                list.add(result.similar)
            }

            if (result.recommendations.results.isNotEmpty()) {
                list.add(result.recommendations)
            }

            if (result.reviews.results.isNotEmpty()) {
                list.add(result.reviews)
            }

            _details.postValue(list)
        }
    }
}