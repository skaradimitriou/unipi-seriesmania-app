package com.stathis.seriesmania.ui.results.results

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.stathis.core.base.BaseViewModel
import com.stathis.data.api.SeriesApi
import com.stathis.data.pagingsource.TvSeriesPagingSource
import com.stathis.domain.model.ResultType
import com.stathis.domain.model.UiModel
import com.stathis.domain.model.genres.Genre
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResultsViewModel @Inject constructor(
    app: Application,
    private val api: SeriesApi
) : BaseViewModel(app) {

    private val _query = MutableLiveData<ResultType>()
    private var genre: Genre? = null

    val data: LiveData<PagingData<UiModel>>
        get() = _query.switchMap { type ->
            Pager(
                config = PagingConfig(20),
                pagingSourceFactory = {
                    TvSeriesPagingSource(api, genre, type)
                }
            ).liveData.cachedIn(viewModelScope)
        }

    fun getResultsForGenre(genre: Genre? = null, resultType: ResultType) {
        this.genre = genre
        _query.postValue(resultType)
    }
}