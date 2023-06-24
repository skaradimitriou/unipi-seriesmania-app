package com.stathis.seriesmania.ui.results.details

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.stathis.core.base.BaseViewModel
import com.stathis.domain.combiners.SeriesDetailsCombiner
import com.stathis.domain.model.Result
import com.stathis.domain.model.TvSeries
import com.stathis.domain.model.UiModel
import com.stathis.domain.usecases.watchlist.AddToWatchlistUseCase
import com.stathis.domain.usecases.watchlist.DeleteItemFromWatchlistUseCase
import com.stathis.domain.usecases.watchlist.FetchWatchlistUseCase
import com.stathis.seriesmania.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    app: Application,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val combiner: SeriesDetailsCombiner,
    private val addToWatchlistUseCase: AddToWatchlistUseCase,
    private val removeFromWatchlistUseCase: DeleteItemFromWatchlistUseCase,
    private val fetchWatchlistUseCase: FetchWatchlistUseCase
) : BaseViewModel(app) {

    val details: LiveData<Result<List<UiModel>>>
        get() = _details

    private val _details = MutableLiveData<Result<List<UiModel>>>()

    val isFavorite: LiveData<Result<Boolean>>
        get() = _isFavorite

    private val _isFavorite = MutableLiveData<Result<Boolean>>(Result.Success(false))

    private var _favoriteState = false

    private var _currentItem: TvSeries? = null

    init {
        fetchUserWatchlist()
    }

    fun getSeriesInfo(series: TvSeries) {
        viewModelScope.launch(dispatcher) {
            _currentItem = series

            _details.postValue(Result.Loading())

            val result = combiner.invoke(series.id)

            val list = mutableListOf<UiModel>()
            list.add(result.details)

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

            _details.postValue(Result.Success(list))
        }
    }

    private fun fetchUserWatchlist() {
        viewModelScope.launch(dispatcher) {
            fetchWatchlistUseCase.invoke().collect {
                val isFavorite = it.contains(_currentItem)
                _favoriteState = isFavorite
                _isFavorite.postValue(Result.Success(isFavorite))
            }
        }
    }

    fun favoriteIconClicked() {
        _isFavorite.postValue(Result.Loading())
        viewModelScope.launch(dispatcher) {
            if (_favoriteState) {
                removeFromWatchlistUseCase.invoke(_currentItem)
                fetchUserWatchlist()
            } else {
                addToWatchlistUseCase.invoke(_currentItem)
                fetchUserWatchlist()
            }
        }
    }
}