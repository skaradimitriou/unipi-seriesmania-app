package com.stathis.seriesmania.ui.results.details

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.stathis.core.base.BaseViewModel
import com.stathis.core.util.auth.Authenticator
import com.stathis.domain.combiners.SeriesDetailsCombiner
import com.stathis.domain.model.Result
import com.stathis.domain.model.TvSeries
import com.stathis.domain.model.UiModel
import com.stathis.domain.model.details.RatingPromoModel
import com.stathis.domain.model.reviews.Rating
import com.stathis.domain.usecases.ratings.AddNewRatingUseCase
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
    private val fetchWatchlistUseCase: FetchWatchlistUseCase,
    private val addNewRatingUseCase: AddNewRatingUseCase,
    private val auth: Authenticator
) : BaseViewModel(app) {

    val details: LiveData<Result<List<UiModel>>>
        get() = _details

    private val _details = MutableLiveData<Result<List<UiModel>>>()

    val ratingInProgress: LiveData<Result<Boolean>>
        get() = _ratingInProgress

    private val _ratingInProgress = MutableLiveData<Result<Boolean>>()

    val isFavorite: LiveData<Result<Boolean>>
        get() = _isFavorite

    private val _isFavorite = MutableLiveData<Result<Boolean>>(Result.Success(false))

    private var _favoriteState = false
    private var _currentItem: TvSeries? = null

    init {
        fetchUserWatchlist()
    }

    fun getSeriesInfo(series: TvSeries) {
        _details.postValue(Result.Loading())
        viewModelScope.launch(dispatcher) {
            _currentItem = series

            val result = combiner.invoke(series.id)
            val list = mutableListOf<UiModel>()
            list.add(result.details)

            val model = if (result.ratingMadeForThisSeries) {
                RatingPromoModel(
                    title = "Rate your experience",
                    description = "You've already rated ${series.name}, but we'd love to know if your opinion has changed since then.",
                    ctaText = "Rate again"
                )
            } else {
                RatingPromoModel(
                    title = "Rate your experience",
                    description = "Help us improve by sharing your thoughts on the series your just watched.",
                    ctaText = "Rate now"
                )
            }

            list.add(model)

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

    fun rateSeries(rating: Double) {
        _ratingInProgress.postValue(Result.Loading())
        viewModelScope.launch(dispatcher) {
            val data = Rating(
                userId = auth.getActiveUserId(),
                seriesId = _currentItem?.id.toString(),
                value = rating
            )

            addNewRatingUseCase.invoke(data).collect {
                _ratingInProgress.postValue(Result.Success(it))
            }
        }
    }
}