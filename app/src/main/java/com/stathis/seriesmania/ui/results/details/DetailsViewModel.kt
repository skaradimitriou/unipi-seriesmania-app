package com.stathis.seriesmania.ui.results.details

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.stathis.domain.usecases.cast.GetCastForSeriesUseCase
import com.stathis.domain.usecases.reviews.GetReviewsForSeriesUseCase
import com.stathis.seriesmania.base.BaseViewModel
import com.stathis.seriesmania.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    app: Application,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val castUseCase: GetCastForSeriesUseCase,
    private val reviewsUseCase: GetReviewsForSeriesUseCase
) : BaseViewModel(app) {


    fun getCastBySeriesId(seriesId: Int) {
        viewModelScope.launch(dispatcher) {
            val result = castUseCase.invoke(seriesId)
            Timber.d("CAST => $result")
        }
        viewModelScope.launch(dispatcher) {
            val result = reviewsUseCase.invoke(seriesId)
            Timber.d("REVIEWS => $result")
        }
    }
}